package org.example.service.impl.trainer;

import com.example.dto.TrainerWorkingHoursRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TrainerWorkingHoursMessagingService implements TrainerWorkingHoursService {

    private static final String TRAINER_WORKING_HOURS_QUEUE = "trainer.working.hours.queue";

    private JmsTemplate jmsTemplate;

    @Override
    public void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto) {
        log.info("Sending a message - {}, to the queue - {}", requestDto, TRAINER_WORKING_HOURS_QUEUE);
        jmsTemplate.convertAndSend(TRAINER_WORKING_HOURS_QUEUE, requestDto);
        log.info("Successfully sent a message - {}, to the queue - {}", requestDto, TRAINER_WORKING_HOURS_QUEUE);
    }
}
