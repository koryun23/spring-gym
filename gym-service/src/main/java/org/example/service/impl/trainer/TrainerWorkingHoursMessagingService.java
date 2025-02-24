package org.example.service.impl.trainer;

import com.example.dto.TrainerWorkingHoursRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.example.service.core.trainer.TrainerWorkingHoursService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class TrainerWorkingHoursMessagingService implements TrainerWorkingHoursService {

    @Value("${jms.trainer-working-hours-queue}")
    private String trainerWorkingHoursQueue;

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
    @Transactional
    @Override
    public void updateWorkingHours(TrainerWorkingHoursRequestDto requestDto) {
        log.info("Sending a message - {}, to the queue - {}", requestDto, trainerWorkingHoursQueue);
        jmsTemplate.convertAndSend(trainerWorkingHoursQueue, requestDto);
        log.info("Successfully sent a message - {}, to the queue - {}", requestDto, trainerWorkingHoursQueue);
    }
}
