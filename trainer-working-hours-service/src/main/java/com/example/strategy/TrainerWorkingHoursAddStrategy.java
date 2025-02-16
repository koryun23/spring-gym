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
    public TrainerEntity updateTrainerWorkingHoursAndGet(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer entity must not be null");
        log.info("Adding to the working hours of {}", trainerEntity.getTrainerUsername());

        Optional<TrainerEntity> optionalTrainerEntity =
            trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear(
                trainerEntity.getTrainerUsername(),
                trainerEntity.getTrainingMonth(),
                trainerEntity.getTrainingYear()
            );

        if (optionalTrainerEntity.isPresent()) {
            log.info("Trainer's working hours are already registered in the month {} of year {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
            TrainerEntity persistedTrainerEntity = optionalTrainerEntity.get();
            log.info("Adding {} to the current working hours({})", trainerEntity.getDuration(),
                persistedTrainerEntity.getDuration());
            trainerRepository.updateWorkingHours(persistedTrainerEntity.getTrainerUsername(),
                persistedTrainerEntity.getDuration() + trainerEntity.getDuration());
            log.info("Successfully persisted trainer entity - {}", persistedTrainerEntity);
        } else {
            log.info("Registering trainer's working hours in the month {} of year {}", trainerEntity.getTrainingMonth(),
                trainerEntity.getTrainingYear());
            trainerRepository.save(trainerEntity);
            log.info("Successfully registered trainer's working hours in the month {} of year {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
        }

        return trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear(
            trainerEntity.getTrainerUsername(),
            trainerEntity.getTrainingMonth(),
            trainerEntity.getTrainingYear()
        ).orElseThrow(() -> new TrainerWorkingHoursUpdateException(
            "Trainer working hours with the given username, month and year does not exist"));
    }
}
