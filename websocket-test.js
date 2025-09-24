import ws from 'k6/ws';
import { check, sleep, group } from 'k6';
import { Counter } from 'k6/metrics';

// --- 테스트 환경 설정 ---
const PLAN_IDS = [1, 2, 3, 4, 5]; // 여러 Plan ID를 배열로 지정
const BASE_URL = 'ws://localhost:8080/ws'; // 순수 WebSocket 엔드포인트

// --- 사용자 정의 지표 ---
const errorCounter = new Counter('errors_total');

// --- 부하 시나리오 설정 ---
export const options = {
    stages: [
        { duration: '30s', target: 1000 }, // 30초에 걸쳐 1000명까지 증가
        { duration: '1m', target: 1000 },  // 1분 유지
        { duration: '20s', target: 0 },   // 20초에 걸쳐 감소
    ],
    thresholds: {
        'errors_total': ['count<50'], // 에러 허용치
        'checks': ['rate>0.95'],
    },
};

// --- STOMP 프레임 전송 함수 ---
const stompSend = (socket, command, destination, body = '', extraHeaders = {}) => {
    const headers = { destination, ...extraHeaders };
    if (body) {
        headers['content-type'] = 'application/json';
        headers['content-length'] = body.length;
    }
    const frame = `${command}\n${Object.entries(headers)
        .map(([k, v]) => `${k}:${v}`)
        .join('\n')}\n\n${body}\0`;
    socket.send(frame);
};

// --- 메인 시나리오 ---
export default function () {
    // 각 VU가 PLAN_IDS 중 하나를 선택 (라운드로빈)
    const planId = PLAN_IDS[__VU % PLAN_IDS.length];
    const url = `${BASE_URL}`;
    const params = {};

    const res = ws.connect(url, params, function (socket) {
        let isConnected = false;

        socket.on('open', () => {
            const connectFrame = 'CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\0';
            socket.send(connectFrame);
        });

        socket.on('message', (rawMsg) => {
            if (rawMsg.includes('CONNECTED')) {
                isConnected = true;

                // 구독 설정 (VU마다 planId가 다름)
                stompSend(socket, 'SUBSCRIBE', `/topic/plans/${planId}/waypoints`, '', { id: `sub-wp-${__VU}` });
                stompSend(socket, 'SUBSCRIBE', `/topic/plans/${planId}/memos`, '', { id: `sub-memo-${__VU}` });
                stompSend(socket, 'SUBSCRIBE', `/topic/plans/${planId}/routes`, '', { id: `sub-route-${__VU}` });
                stompSend(socket, 'SUBSCRIBE', `/user/queue/errors`, '', { id: `sub-error-${__VU}` });
            } else if (rawMsg.includes('ERROR')) {
                errorCounter.add(1);
            }
        });

        socket.on('error', () => {
            errorCounter.add(1);
        });

        // --- 실제 테스트 시나리오 ---
        socket.setTimeout(() => {
            if (!isConnected) {
                socket.close();
                return;
            }

            group(`1. Initial Data Load (plan=${planId})`, function () {
                stompSend(socket, 'SEND', `/app/plans/${planId}/waypoints/init`);
                stompSend(socket, 'SEND', `/app/plans/${planId}/memos/init`);
                stompSend(socket, 'SEND', `/app/plans/${planId}/routes/init`);
                sleep(1);
            });

            group(`2. Waypoint Create (plan=${planId})`, function () {
                const now = new Date();
                const startTime = now.toISOString();
                now.setHours(now.getHours() + 1);
                const endTime = now.toISOString();

                const createPayload = {
                    name: `K6 WP ${__VU}-${__ITER}`,
                    description: 'Created by k6',
                    address: 'k6 test city',
                    locationCategory: 'DEFAULT',
                    xPosition: Math.random() * 800,
                    yPosition: Math.random() * 600,
                    startTime: startTime,
                    endTime: endTime,
                };
                stompSend(socket, 'SEND', `/app/plans/${planId}/waypoints/create`, JSON.stringify(createPayload));
                sleep(1);
            });

            group(`3. Memo Create (plan=${planId})`, function () {
                const createPayload = {
                    title: `My Memo VU=${__VU}`,
                    content: 'Memo content.',
                    xPosition: Math.random() * 800,
                    yPosition: Math.random() * 600,
                };
                stompSend(socket, 'SEND', `/app/plans/${planId}/memos/create`, JSON.stringify(createPayload));
                sleep(1);
            });

            // 30초 후 연결 종료
            socket.setTimeout(() => {
                stompSend(socket, 'DISCONNECT', '', '');
                socket.close();
            }, 30000);

        }, 3000);
    });

    check(res, { 'WebSocket connection successful': (r) => r && r.status === 101 });
}