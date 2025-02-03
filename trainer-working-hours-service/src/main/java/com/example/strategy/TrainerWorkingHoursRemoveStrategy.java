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
        log.info("Adding to the working hours of {}", trainerEntity.getTrainerUsername());

        Optional<TrainerEntity> optionalTrainerEntity = trainerRepository.findByUsernameAndMonthAndYear(
            trainerEntity.getTrainerUsername(),
            trainerEntity.getTrainingMonth(),
            trainerEntity.getTrainingYear()
        );

        TrainerEntity updatedTrainerEntity = null;
        if (optionalTrainerEntity.isPresent()) {
            TrainerEntity persistedTrainerEntity = optionalTrainerEntity.get();
            persistedTrainerEntity.setDuration(persistedTrainerEntity.getDuration() - trainerEntity.getDuration());
            updatedTrainerEntity = trainerRepository.save(persistedTrainerEntity);
        } else {
            updatedTrainerEntity = trainerRepository.save(trainerEntity);
        }

        log.info("Successfully added to the working hours of {}, new value - {}", trainerEntity.getTrainerUsername(),
            updatedTrainerEntity.getDuration());
        return updatedTrainerEntity;
    }
}
