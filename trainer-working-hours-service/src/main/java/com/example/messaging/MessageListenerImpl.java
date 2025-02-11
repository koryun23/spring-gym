package com.example.messaging;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.dto.TrainerWorkingHoursRetrievalRequestDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import com.example.service.TrainerService;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListenerImpl {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final TrainerWorkingHoursUpdateStrategyFactory strategyFactory;
    private final MessageSenderImpl messageSender;

    /**
     * Constructor.
     */
    public MessageListenerImpl(TrainerService trainerService, TrainerMapper trainerMapper,
                               TrainerWorkingHoursUpdateStrategyFactory strategyFactory,
                               MessageSenderImpl messageSender) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
        this.strategyFactory = strategyFactory;
        this.messageSender = messageSender;
    }

    /**
     * Method for updating working hours of a trainer once a message is received.
     *
     * @param body Message
     */
    @JmsListener(destination = "trainer.working.hours.update.queue", containerFactory = "jmsListenerContainerFactory")
    public void onUpdateTrainerWorkingHours(TrainerWorkingHoursRequestDto body) {
        log.info("Received a message - {}", body);

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(body);
        TrainerWorkingHoursUpdateStrategy strategy = strategyFactory.getStrategy(body.getActionType());

        trainerService.updateWorkingHours(trainerEntity, strategy);

        log.info("Successfully updated working hours of the provided trainer");
    }

    /**
     * Method for sending trainer working hours to the specified message queue upon request from the main microservice.
     *
     * @param body TrainerWorkingHoursRetrievalRequestDto
     */
    @JmsListener(destination = "trainer.working.hours.read.queue", containerFactory = "jmsListenerContainerFactory")
    public void onReadTrainerWorkingHours(TrainerWorkingHoursRetrievalRequestDto body) {

        log.info("Received a message - {}", body);

        List<TrainerEntity> all =
            trainerService.findAllByUsernameAndMonthAndYear(body.getUsername(), body.getMonth(), body.getYear());

        log.info(
            "Retrieved working hours of trainer according to the TrainerWorkingHoursRetrievalRequestDto - {}, result - {}",
            body, all);

        messageSender.sendTrainerWorkingHours(all.stream().map(trainerMapper::mapTrainerEntityToTrainerDto).toList());

        log.info("Successfully sent trainer working hours - {}", all);
    }
}
