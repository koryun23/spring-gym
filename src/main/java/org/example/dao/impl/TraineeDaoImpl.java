package org.example.dao.impl;

import org.example.dao.core.TraineeDao;
import org.example.entity.Trainee;
import org.example.repository.impl.TraineeStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.Optional;

public class TraineeDaoImpl implements TraineeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    private TraineeStorageImpl storage;

    @Autowired
    public void setStorage(TraineeStorageImpl storage) {
        this.storage = storage;
    }

    @Override
    public Trainee get(Long id) {
        Assert.notNull(id, "Trainee id must not be null");
        LOGGER.info("Retrieving a Trainee with an id of {}", id);
        Trainee trainee = storage.get(id);
        LOGGER.info("Successfully retrieved a Trainee with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public Trainee save(Trainee trainee) {
        Assert.notNull(trainee, "Trainee must not be null");
        LOGGER.info("Saving {}", trainee);
        Trainee addedTrainee = storage.add(trainee);
        LOGGER.info("Successfully saved {}", trainee);
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        Assert.notNull(trainee, "Trainee must not be null");
        LOGGER.info("Updating a Trainee with an id of {}", trainee.getUserId());
        Trainee updatedTrainee = storage.update(trainee);
        LOGGER.info("Successfully updated a Trainee with an id of {}, reslut - {}", trainee.getUserId(), updatedTrainee);
        return updatedTrainee;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Trainee Id must not be null");
        LOGGER.info("Deleting a trainee with an id of {}", id);
        boolean remove = storage.remove(id);
        if(remove) LOGGER.info("Successfully removed a trainee with an id of {}", id);
        else LOGGER.error("Failed to remove a trainee with an id of {}", id);
        return remove;
    }

    @Override
    public Trainee getByUsername(String username) {
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");
        LOGGER.info("Retrieving a Trainee with a username of {}", username);
        Trainee trainee = storage.getByUsername(username);
        LOGGER.info("Successfully retrieved a Trainee with a username of {}, result - {}", username, trainee);
        return trainee;
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        Assert.notNull(username, "Trainee username must not be null");
        Assert.notNull(username, "Trainee username must not be empty");
        LOGGER.info("Retrieving an optional Trainee with a username of {}", username);
        Optional<Trainee> optionalTrainee = storage.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional Trainee with a username of {}, result - {}", username, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        Assert.notNull(id, "Trainee id must not be null");
        LOGGER.info("Retrieving an optional Trainee with an id of {}", id);
        Optional<Trainee> optionalTrainee = storage.findById(id);
        LOGGER.info("Successfully retrieved an optional Trainee with an id {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }
}
