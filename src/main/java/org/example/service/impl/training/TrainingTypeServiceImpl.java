package org.example.service.impl.training;

import java.util.List;
import java.util.Optional;
import org.example.entity.training.TrainingTypeEntity;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.repository.TrainingTypeEntityRepository;
import org.example.service.core.training.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    private final TrainingTypeEntityRepository trainingTypeDao;

    public TrainingTypeServiceImpl(TrainingTypeEntityRepository trainingTypeDao) {
        this.trainingTypeDao = trainingTypeDao;
    }

    @Transactional
    @Override
    public TrainingTypeEntity get(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving a Training Type Entity with an id of {}", id);
        TrainingTypeEntity trainingTypeEntity =
            trainingTypeDao.findById(id).orElseThrow(() -> new TrainingTypeNotFoundException(id));
        LOGGER.info("Successfully retrieved a Training Type Entity with an id of {}, result - {}",
            id, trainingTypeEntity);
        return trainingTypeEntity;
    }

    @Transactional
    @Override
    public Optional<TrainingTypeEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional Training Type Entity with an id of {}", id);
        Optional<TrainingTypeEntity> optionalTrainingType = trainingTypeDao.findById(id);
        LOGGER.info("Successfully retrieved an optional Training Type Entity with an id of {}, result - {}",
            id, optionalTrainingType);
        return optionalTrainingType;
    }

    @Transactional
    @Override
    public List<TrainingTypeEntity> findAll() {
        LOGGER.info("Retrieving all training types");

        List<TrainingTypeEntity> all = trainingTypeDao.findAll();

        LOGGER.info("Successfully retrieved all training types, result - {}", all);
        return all;
    }
}
