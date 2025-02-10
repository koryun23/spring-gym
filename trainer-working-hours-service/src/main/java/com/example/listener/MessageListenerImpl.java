package com.example.listener;

import com.example.dto.TrainerWorkingHoursRequestDto;
import com.example.entity.TrainerEntity;
import com.example.exception.MessageConversionException;
import com.example.mapper.TrainerMapper;
import com.example.service.TrainerService;
import com.example.strategy.TrainerWorkingHoursUpdateStrategy;
import com.example.strategy.TrainerWorkingHoursUpdateStrategyFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageListenerImpl {

    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;
    private final TrainerWorkingHoursUpdateStrategyFactory strategyFactory;
    private final ObjectMapper objectMapper;

    /**
     * Constructor.
     */
    public MessageListenerImpl(TrainerService trainerService, TrainerMapper trainerMapper,
                               TrainerWorkingHoursUpdateStrategyFactory strategyFactory, ObjectMapper objectMapper) {
        this.trainerService = trainerService;
        this.trainerMapper = trainerMapper;
        this.strategyFactory = strategyFactory;
        this.objectMapper = objectMapper;
    }

    /**
     * Method for updating working hours of a trainer once a message is received.
     *
     * @param body Message
     */
    @JmsListener(destination = "trainer.working.hours.queue", containerFactory = "jmsListenerContainerFactory")
    public void onMessage(TrainerWorkingHoursRequestDto body) {
        log.info("Received a message - {}", body);

        //TrainerWorkingHoursRequestDto body = getBodyFromMessage(body);

        TrainerEntity trainerEntity = trainerMapper.mapTrainerWorkingHoursRequestDtoToTrainerEntity(body);
        TrainerWorkingHoursUpdateStrategy strategy = strategyFactory.getStrategy(body.getActionType());

        trainerService.updateWorkingHours(trainerEntity, strategy);

        log.info("Successfully updated working hours of the provided trainer");
    }

    private TrainerWorkingHoursRequestDto getBodyFromMessage(String message) {

        TrainerWorkingHoursRequestDto body = null;
        try {
            body = objectMapper.readValue(message, TrainerWorkingHoursRequestDto.class);
        } catch (JsonProcessingException e) {
            log.error("Could not convert provided message to TrainerWorkingHoursRequestDto object");
            throw new MessageConversionException(
                "Could not convert provided message to TrainerWorkingHoursRequestDto object");
        }

        if (body == null) {
            log.error("Could not convert provided message to TrainerWorkingHoursRequestDto object");
            throw new MessageConversionException(
                "Could not convert provided message to TrainerWorkingHoursRequestDto object");
        }

        log.info("Successfully converted the provided message to TrainerWorkingHoursRequestDto object");
        return body;

    }
}
