package com.example.strategy;

import com.example.dto.ActionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Slf4j
@Component
public class TrainerWorkingHoursUpdateStrategyFactory {

    private final TrainerWorkingHoursAddStrategy trainerWorkingHoursAddStrategy;
    private final TrainerWorkingHoursRemoveStrategy trainerWorkingHoursRemoveStrategy;

    public TrainerWorkingHoursUpdateStrategyFactory(TrainerWorkingHoursAddStrategy trainerWorkingHoursAddStrategy,
                                                    TrainerWorkingHoursRemoveStrategy trainerWorkingHoursRemoveStrategy) {
        this.trainerWorkingHoursAddStrategy = trainerWorkingHoursAddStrategy;
        this.trainerWorkingHoursRemoveStrategy = trainerWorkingHoursRemoveStrategy;
    }

    /**
     * Method for obtaining the correct strategy.
     */
    public TrainerWorkingHoursUpdateStrategy getStrategy(ActionType actionType) {
        Assert.notNull(actionType, "Action type must not be null - it must either be ADD or DELETE");
        log.info("Picking a TrainerWorkingHoursUpdateStrategy based on ActionType {}", actionType);
        if (actionType == ActionType.ADD) {
            log.info("Picked TrainerWorkingHoursAddStrategy based on Action Type {}", actionType);
            return trainerWorkingHoursAddStrategy;
        } else if (actionType == ActionType.DELETE) {
            log.info("Picked TrainerWorkingHoursRemoveStrategy based on Action Type {}", actionType);
            return trainerWorkingHoursRemoveStrategy;
        }
        log.error("Failed to pick a TrainerWorkingHoursUpdateStrategy based on Action Type {}", actionType);
        throw new IllegalArgumentException("Action type must be either ADD or DELETE");
    }
}
