package org.example.service.impl;

import org.example.dao.impl.TrainerDao;
import org.example.entity.Trainer;
import org.example.service.core.TrainerService;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    private TrainerDao trainerDao;

    @Override
    public Trainer create(TrainerCreateParams params) {
        LOGGER.info("Creating a Trainer based on TrainerCreateParams - {}", params);
        Trainer trainer = trainerDao.save(new Trainer(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getSpecializationType()
        ));
        LOGGER.info("Successfully created a Trainer based on TrainerCreateParams - {}, result - {}", params, trainer);
        return trainer;
    }

    @Override
    public Trainer update(TrainerUpdateParams params) {
        LOGGER.info("Updating a Trainer based on TrainerUpdateParams - {}", params);
        Trainer trainer = trainerDao.update(new Trainer(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getSpecializationType()
        ));
        LOGGER.info("Successfully updated a Trainer based on TrainerUpdateParams - {}, result - {}", params, trainer);
        return trainer;
    }

    @Override
    public Trainer select(Long trainerId) {
        LOGGER.info("Selecting a Trainer with an id of {}", trainerId);
        Trainer trainer = trainerDao.get(trainerId);
        LOGGER.info("Successfully selected a Trainer with an id of {}, result - {}", trainerId, trainer);
        return trainer;
    }
}
