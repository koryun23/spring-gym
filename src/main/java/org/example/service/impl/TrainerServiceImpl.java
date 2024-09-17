package org.example.service.impl;

import java.util.Optional;
import org.example.dao.impl.TrainerDaoImpl;
import org.example.entity.TrainerEntity;
import org.example.service.core.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    private TrainerDaoImpl trainerDao;

    @Override
    public TrainerEntity create(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerCreateParams must not be null");
        LOGGER.info("Creating a TrainerEntity based on TrainerCreateParams - {}", trainerEntity);
        TrainerEntity createdTrainerEntity = trainerDao.save(trainerEntity);
        LOGGER.info("Successfully created a TrainerEntity based on TrainerCreateParams - {}, result - {}",
            trainerEntity,
            createdTrainerEntity);
        return createdTrainerEntity;
    }

    @Override
    public TrainerEntity update(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerUpdateParams must not be null");
        LOGGER.info("Updating a TrainerEntity based on TrainerUpdateParams - {}", trainerEntity);
        TrainerEntity updatedTrainerEntity = trainerDao.update(trainerEntity);
        LOGGER.info("Successfully updated a TrainerEntity based on TrainerUpdateParams - {}, result - {}",
            trainerEntity,
            updatedTrainerEntity);
        return updatedTrainerEntity;
    }

    @Override
    public TrainerEntity select(Long trainerId) {
        Assert.notNull(trainerId, "TrainerEntity id must not be null");
        LOGGER.info("Selecting a TrainerEntity with an id of {}", trainerId);
        TrainerEntity trainerEntity = trainerDao.get(trainerId);
        LOGGER.info("Successfully selected a TrainerEntity with an id of {}, result - {}", trainerId, trainerEntity);
        return trainerEntity;
    }

    @Override
    public TrainerEntity selectByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Selecting a TrainerEntity with a username of {}", username);
        TrainerEntity trainerEntity = trainerDao.getByUsername(username);
        LOGGER.info("Successfully selected a TrainerEntity with a username of {}, result - {}", username,
            trainerEntity);
        return trainerEntity;
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainerEntity with an id of {}", id);
        Optional<TrainerEntity> optionalTrainer = trainerDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with an id of {}, result - {}", id,
            optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieved an optional TrainerEntity with a username of {}", username);
        Optional<TrainerEntity> optionalTrainer = trainerDao.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username,
            optionalTrainer);
        return optionalTrainer;
    }
}
