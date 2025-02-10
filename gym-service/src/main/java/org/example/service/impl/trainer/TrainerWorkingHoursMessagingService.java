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

    private final JmsTemplate jmsTemplate;

    /**
     * Constructor.
     *
     * @param jmsTemplate JmsTemplate
     */
    public TrainerWorkingHoursMessagingService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Method for sending a message to the specified message queue to update the working horus of a trainer.
     *
     * @param requestDto TrainerWorkingHoursRequestDto
     */
    @Override
    public void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto) {
        log.info("Sending a message - {}, to the queue - {}", requestDto, TRAINER_WORKING_HOURS_QUEUE);
        jmsTemplate.convertAndSend(TRAINER_WORKING_HOURS_QUEUE, requestDto);
        log.info("Successfully sent a message - {}, to the queue - {}", requestDto, TRAINER_WORKING_HOURS_QUEUE);
    }
}
