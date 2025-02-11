package com.example.messaging;

import com.example.dto.TrainerDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageSenderImpl {

    private static final String TRAINER_WORKING_HOURS_READ_QUEUE = "trainer.working.hours.read.queue";

    private final JmsTemplate jmsTemplate;

    public MessageSenderImpl(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Method for sending trainer dtos to the queue trainer.working.hours.read.queue.
     *
     * @param trainerDtos List of trainer dtos
     */
    public void sendTrainerWorkingHours(List<TrainerDto> trainerDtos) {
        log.info("Sending trainer entities - {}, to the queue - {}", trainerDtos, TRAINER_WORKING_HOURS_READ_QUEUE);
        jmsTemplate.convertAndSend(TRAINER_WORKING_HOURS_READ_QUEUE, trainerDtos);
        log.info("Successfully sent trainer entities - {}, to the queue - {}", trainerDtos,
            TRAINER_WORKING_HOURS_READ_QUEUE);

    }
}
