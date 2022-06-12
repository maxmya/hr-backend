package com.workmotion.hrbackend.core.application.common.exception;


public class EventNotSupportedException extends RuntimeException {
    public EventNotSupportedException(String message) {
        super(message);
    }

}
