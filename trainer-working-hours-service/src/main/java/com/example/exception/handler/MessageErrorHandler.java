package com.example.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ErrorHandler;

@Slf4j
public class MessageErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        log.error("Failed to send a message, cause - {}, exception message - {}", t.getCause(), t.getMessage());
    }
}
