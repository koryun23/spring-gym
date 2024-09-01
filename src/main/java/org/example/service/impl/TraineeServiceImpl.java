package org.example.service.impl;

import org.example.dao.core.TraineeDao;
import org.example.dao.impl.TraineeDaoImpl;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.service.core.TraineeService;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeDao traineeDao;

    @Override
    public Trainee create(TraineeCreateParams params) {
        Assert.notNull(params, "TraineeCreateParams must not be null");
        LOGGER.info("Creating a Trainee based on TraineeCreateParams - {}", params);
        Trainee trainee = traineeDao.save(new Trainee(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getDateOfBirth(),
                params.getAddress()
        ));
        LOGGER.info("Successfully created a Trainee based on TraineeCreateParams - {}, result - {}", params, trainee);
        return trainee;
    }

    @Override
    public Trainee update(TraineeUpdateParams params) {
        Assert.notNull(params, "TraineeCreateParams must not be null");
        LOGGER.info("Updating a Trainee based on TraineeUpdateParams - {}", params);
        Trainee trainee = traineeDao.update(new Trainee(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getDateOfBirth(),
                params.getAddress()
        ));
        LOGGER.info("Successfully updated a Trainee based on TraineeUpdateParams - {}, result - {}", params, trainee);
        return trainee;
    }

    @Override
    public boolean delete(Long traineeId) {
        Assert.notNull(traineeId, "Trainee id must not be null");
        LOGGER.info("Deleting a Trainee with an id of {}", traineeId);
        boolean success = traineeDao.delete(traineeId);
        if (success) {
            LOGGER.info("Successfully deleted a trainee with an id of {}", traineeId);
        } else {
            LOGGER.error("Failed to delete a trainee with an id of {}", traineeId);
            throw new TraineeNotFoundException(traineeId);
        }
        return success;
    }

    @Override
    public Trainee select(Long traineeId) {
        Assert.notNull(traineeId, "Trainee id must not be null");
        LOGGER.info("Selecting a Trainee with an id of {}", traineeId);
        Trainee trainee = traineeDao.get(traineeId);
        LOGGER.info("Successfully selected a Trainee with an id of {}, result - {}", traineeId, trainee);
        return trainee;
    }

    @Override
    public Trainee selectByUsername(String username) {
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");
        LOGGER.info("Selecting a Trainee with a username of {}", username);
        Trainee trainee = traineeDao.getByUsername(username);
        LOGGER.info("Successfully selected a trainee with a username of {}, result - {}", username, trainee);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        Assert.notNull(id, "Trainee id must not be null");
        LOGGER.info("Retrieved an optional Trainee with an id of {}", id);
        Optional<Trainee> optionalTrainee = traineeDao.findById(id);
        LOGGER.info("Successfully retrieved an optional Trainee with an id of {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");
        LOGGER.info("Retrieved an optional Trainee with a username of {}", username);
        Optional<Trainee> optionalTrainee = traineeDao.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional Trainee with an username of {}, result - {}", username, optionalTrainee);
        return optionalTrainee;
    }
}
