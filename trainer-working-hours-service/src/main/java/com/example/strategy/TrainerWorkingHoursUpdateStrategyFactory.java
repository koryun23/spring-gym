package com.example.strategy;

import com.example.dto.ActionType;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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
        if (actionType == ActionType.ADD) {
            return trainerWorkingHoursAddStrategy;
        } else if (actionType == ActionType.DELETE) {
            return trainerWorkingHoursRemoveStrategy;
        }
        throw new IllegalArgumentException("Action type must be either ADD or DELETE");
    }
}
