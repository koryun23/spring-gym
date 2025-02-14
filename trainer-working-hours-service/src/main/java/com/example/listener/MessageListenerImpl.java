package com.example.listener;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.entity.TrainerEntity;
import com.example.mapper.TrainerMapper;
import com.example.service.TrainerService;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class MessageListenerImpl {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final TrainerWorkingHoursUpdateStrategyFactory strategyFactory;

    /**
     * Constructor.
     */
    public MessageListenerImpl(TrainerService trainerService, TrainerMapper trainerMapper,
                               TrainerWorkingHoursUpdateStrategyFactory strategyFactory) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
        this.strategyFactory = strategyFactory;
    }

    /**
     * Method for updating working hours of a trainer once a message is received.
     *
     * @param body Message
     */
    //@Transactional
    @JmsListener(destination = "trainer.working.hours.queue", containerFactory = "jmsListenerContainerFactory")
    public void onUpdateTrainerWorkingHours(TrainerWorkingHoursRequestDto body) {
        log.info("Received a message - {}", body);

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(body);
        TrainerWorkingHoursUpdateStrategy strategy = strategyFactory.getStrategy(body.getActionType());

        trainerService.updateWorkingHours(trainerEntity, strategy);

        log.info("Successfully updated working hours of the provided trainer");
    }
}
