package org.example.service.impl;

import org.example.dao.impl.TraineeDaoImpl;
import org.example.entity.Trainee;
import org.example.service.core.TraineeService;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeDaoImpl traineeDao;

    @Override
    public Trainee create(TraineeCreateParams params) {
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
        LOGGER.info("Deleting a Trainee with an id of {}", traineeId);
        boolean success = traineeDao.delete(traineeId);
        if (success) LOGGER.info("Successfully deleted a trainee with an id of {}", traineeId);
        else LOGGER.error("Failed to delete a trainee with an id of {}", traineeId);
        return success;
    }

    @Override
    public Trainee select(Long traineeId) {
        LOGGER.info("Selecting a Trainee with an id of {}", traineeId);
        Trainee trainee = traineeDao.get(traineeId);
        LOGGER.info("Successfully selected a Trainee with an id of {}, result - {}", traineeId, trainee);
        return trainee;
    }
}
