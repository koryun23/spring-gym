package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Trainee;
import org.example.repository.impl.TraineeStorageImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraineeDao implements Dao<Trainee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDao.class);

    @Autowired
    private TraineeStorageImpl storage;

    @Override
    public Trainee get(Long id) {
        LOGGER.info("Retrieving a Trainee with an id of {}", id);
        Trainee trainee = storage.get(id);
        LOGGER.info("Successfully retrieved a Trainee with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public Trainee save(Trainee trainee) {
        LOGGER.info("Saving {}", trainee);
        Trainee addedTrainee = storage.add(trainee);
        LOGGER.info("Successfully saved {}", addedTrainee);
        return addedTrainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        LOGGER.info("Updating a Trainee with an id of {}", trainee.getUserId());
        Trainee updatedTrainee = storage.update(trainee);
        LOGGER.info("Successfully updated a Trainee with an id of {}, reslut - {}", trainee.getUserId(), updatedTrainee);
        return updatedTrainee;
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("Deleting a trainee with an id of {}", id);
        boolean remove = storage.remove(id);
        if(remove) LOGGER.info("Successfully removed a trainee with an id of {}", id);
        else LOGGER.error("Failed to remove a trainee with an id of {}", id);
        return remove;
    }
}
