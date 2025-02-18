package com.example.strategy;

import com.example.entity.TrainerEntity;
import com.example.exception.TrainerWorkingHoursUpdateException;
import com.example.repository.TrainerRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class TrainerWorkingHoursAddStrategy implements TrainerWorkingHoursUpdateStrategy {
    private final TrainerRepository trainerRepository;

    public TrainerWorkingHoursAddStrategy(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public Long updateTrainerWorkingHours(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer entity must not be null");
        log.info("Adding to the working hours of {}", trainerEntity.getTrainerUsername());

        Optional<TrainerEntity> optionalTrainerEntity =
            trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear(
                trainerEntity.getTrainerUsername(),
                trainerEntity.getTrainingMonth(),
                trainerEntity.getTrainingYear()
            );

        Long newDuration = null;

        if (optionalTrainerEntity.isPresent()) {
            log.info("Trainer's working hours are already registered in the month {} of year {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
            TrainerEntity persistedTrainerEntity = optionalTrainerEntity.get();
            log.info("Adding {} to the current working hours({})", trainerEntity.getDuration(),
                persistedTrainerEntity.getDuration());

            newDuration = persistedTrainerEntity.getDuration() + trainerEntity.getDuration();

            trainerRepository.updateWorkingHours(
                persistedTrainerEntity.getTrainerUsername(),
                persistedTrainerEntity.getTrainingMonth(),
                persistedTrainerEntity.getTrainingYear(),
                newDuration);
            log.info("Successfully persisted trainer entity - {}", persistedTrainerEntity);
        } else {
            log.info("Registering trainer's working hours in the month {} of year {}", trainerEntity.getTrainingMonth(),
                trainerEntity.getTrainingYear());
            newDuration = trainerEntity.getDuration();
            trainerRepository.save(trainerEntity);
            log.info("Successfully registered trainer's working hours in the month {} of year {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
        }

        log.info("New training duration summary of the given trainee is {}", newDuration);
        return newDuration;

    }
}
