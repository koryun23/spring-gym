package com.example.strategy;

import com.example.entity.TrainerEntity;
import com.example.repository.TrainerRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class TrainerWorkingHoursRemoveStrategy implements TrainerWorkingHoursUpdateStrategy {

    private final TrainerRepository trainerRepository;

    public TrainerWorkingHoursRemoveStrategy(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerEntity updateTrainerWorkingHours(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "Trainer entity must not be null");
        log.info("Removing from the working hours of {}", trainerEntity.getTrainerUsername());

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByTrainerUsernameAndTrainingMonthAndTrainingYear(
            trainerEntity.getTrainerUsername(),
            trainerEntity.getTrainingMonth(),
            trainerEntity.getTrainingYear()
        );

        TrainerEntity updatedTrainerEntity = null;
        if (optionalTrainerEntity.isPresent()) {
            log.info("Trainer's working hours are already registered in the month {} of year {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear());
            TrainerEntity persistedTrainerEntity = optionalTrainerEntity.get();
            persistedTrainerEntity.setDuration(persistedTrainerEntity.getDuration() - trainerEntity.getDuration());
            log.info("Persisting trainer entity - {}", persistedTrainerEntity);
            updatedTrainerEntity = trainerRepository.save(persistedTrainerEntity);
            log.info("Successfully persisted trainer entity - {}, result - {}", persistedTrainerEntity,
                updatedTrainerEntity);
        } else {
            log.info("Registering trainer's working hours in the month {} of year {}", trainerEntity.getTrainingMonth(),
                trainerEntity.getTrainingYear());
            updatedTrainerEntity = trainerRepository.save(trainerEntity);
            log.info("Successfully registered trainer's working hours in the month {} of year {}, result - {}",
                trainerEntity.getTrainingMonth(), trainerEntity.getTrainingYear(), updatedTrainerEntity);
        }

        log.info("Successfully added to the working hours of {}, new value - {}", trainerEntity.getTrainerUsername(),
            updatedTrainerEntity.getDuration());
        return updatedTrainerEntity;
    }
}
