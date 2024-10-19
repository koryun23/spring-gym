package org.example.service.impl;

import java.util.Random;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.service.core.LoggingService;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoggingServiceImpl implements LoggingService {

    @Override
    public void storeTransactionId() {
        MDC.put("transaction-id", UUID.randomUUID().toString());
    }

    @Override
    public void clear() {
        MDC.clear();
    }
}
