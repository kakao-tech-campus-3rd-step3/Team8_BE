package com.kakaotechcampus.journey_planner.infra.message.publisher;

import com.kakaotechcampus.journey_planner.domain.node.Node;
import com.kakaotechcampus.journey_planner.infra.message.RedisException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.RecordId;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static com.kakaotechcampus.journey_planner.global.exception.ErrorCode.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePublisherManager<T extends Node> {
    private final RedissonClient redissonClient;
    private final RedisTemplate<String, Object> redisTemplate;
    private final HashMapper<Object, byte[], byte[]> hashMapper = new ObjectHashMapper();

    /**
     * This function is controlling the node that client clicked some node that is waypoint, or something.
     *
     * @Param planId - Plan's ID
     * @Param uuid - JourneyPlanner's node ID
     * @Param object - Dto to Extends the Message Object.
     */
    public void controlNode(Long planId, T object, Class<T> clazz) {
        String uuid = object.getUuid();
        RLock lock = getLock(planId, uuid);
        log.info("락을 획득하려 시도합니다...");
        try{
            if(lock.tryLock(5, TimeUnit.SECONDS)){
                log.info("락을 획득히였습니다!");
                storeNode(clazz.getSimpleName(), object);
            }
        }catch(InterruptedException e){
            throw new RedisException(LOCK_TIMEOUT);
        }finally {
            log.info("락을 해제히였습니다!");
            lock.unlock();
        }
        log.info("락 획득 시도를 종료합니다.");
    }

    public void deleteNode(Long planId, T object, Class<T> clazz) {
        String uuid = object.getUuid();
        RLock lock = getLock(planId, uuid);
        try{
            lock.lock();
        }finally{
            lock.unlock();
        }
    }

    private RLock getLock(Long planId, String uuid){
        String lockKey = String.format("lock:plan:%d:node:%s", planId, uuid);
        return redissonClient.getLock(lockKey);
    }

    private void storeNode(String key, T value){
        ObjectRecord<String, T> record = ObjectRecord.create(key, value);
        RecordId recordId = redisTemplate.opsForStream(this.hashMapper).add(record);
        if(recordId == null){
            throw new RedisException(CANNOT_PRODUCE);
        }
    }

    private void deleteNode(String key, T value){
        ObjectRecord<String, T> record = ObjectRecord.create(key, value);
    }
}
