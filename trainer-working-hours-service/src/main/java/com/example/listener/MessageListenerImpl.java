package com.example.listener;

import javax.jms.Message;
import javax.jms.MessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListenerImpl implements MessageListener {

    @Override
    public void onMessage(Message message) {
        log.info("Received a message - {}", message);
    }
}
