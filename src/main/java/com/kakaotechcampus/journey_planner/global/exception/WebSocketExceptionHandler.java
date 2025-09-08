package com.kakaotechcampus.journey_planner.global.exception;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class WebSocketExceptionHandler {

    @MessageExceptionHandler(CustomRuntimeException.class)
    @SendToUser(destinations = "/queue/errors", broadcast = false)
    public WebSocketErrorResponse handleCustomRuntimeException(CustomRuntimeException e) {
        return new WebSocketErrorResponse(e.getCode(), e.getMessage());
    }

}
