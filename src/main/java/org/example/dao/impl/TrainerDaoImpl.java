package org.example.dao.impl;

import java.util.List;
import java.util.Optional;
import org.example.dao.core.TrainerDao;
import org.example.entity.TrainerEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.TrainerEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerDaoImpl implements TrainerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    private TrainerEntityRepository trainerEntityRepository;

    @Autowired
    public void setTrainerEntityRepository(TrainerEntityRepository trainerEntityRepository) {
        this.trainerEntityRepository = trainerEntityRepository;
    }

    @Override
    public TrainerEntity get(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving a TrainerEntity with an id of {}", id);
        TrainerEntity trainerEntity = trainerEntityRepository.findById(id)
            .orElseThrow(() -> new TrainerNotFoundException(id));
        LOGGER.info("Successfully retrieved a TrainerEntity with an id of {}, result - {}", id, trainerEntity);
        return trainerEntity;
    }

    @Override
    public TrainerEntity save(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        LOGGER.info("Saving {}", trainerEntity);
        TrainerEntity addedTrainerEntity = trainerEntityRepository.save(trainerEntity);
        LOGGER.info("Successfully added {}", addedTrainerEntity);
        return addedTrainerEntity;
    }

    @Override
    public TrainerEntity update(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerEntity must not be null");
        LOGGER.info("Updating a TrainerEntity with an id of {}", trainerEntity.getId());
        TrainerEntity updatedTrainerEntity = trainerEntityRepository.update(trainerEntity);
        LOGGER.info("Successfully updated a TrainerEntity with an id of {}, result - {}", trainerEntity.getId(),
            updatedTrainerEntity);
        return updatedTrainerEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Deleting a TrainerEntity with an id of {}", id);
        trainerEntityRepository.deleteById(id);
        LOGGER.info("Successfully deleted a TrainerEntity with an id of {}", id);
        return true;
    }

    @Override
    public TrainerEntity getByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving a TrainerEntity with a username of {}", username);
        TrainerEntity trainerEntity = trainerEntityRepository.findByUsername(username)
            .orElseThrow(() -> new TrainerNotFoundException(username));
        LOGGER.info("Successfully retrieved a TrainerEntity with a username of {}, result - {}", username,
            trainerEntity);
        return trainerEntity;
    }

    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving an optional TrainerEntity with a username of {}", username);
        Optional<TrainerEntity> optionalTrainer = trainerEntityRepository.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username,
            optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all Trainers that are not assigned to trainee with a username of {}", traineeUsername);
        List<TrainerEntity> all =
            trainerEntityRepository.findAllTrainersNotAssignedTo(traineeUsername);
        LOGGER.info("Successfully retrieved all Trainers that are not assigned to trainee with a username of {}, result - {}", traineeUsername, all);
        return all;
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainerEntity with an id of {}", id);
        Optional<TrainerEntity> optionalTrainer = trainerEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with an id od {}, result - {}", id,
            optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public List<TrainerEntity> findAll() {
        LOGGER.info("Retrieving a list of all Trainer Entities");
        List<TrainerEntity> all = trainerEntityRepository.findAll();
        LOGGER.info("Successfully retrieved a list of all Trainer Entities, result - {}", all);
        return all;
    }
}
