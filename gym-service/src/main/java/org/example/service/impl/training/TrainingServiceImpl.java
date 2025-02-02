package org.example.service.impl.training;

import java.sql.Date;
import java.util.List;
import org.example.dto.plain.TrainingDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.training.TrainingEntity;
import org.example.repository.TrainingEntityRepository;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TrainingServiceImpl implements TrainingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private final TrainingEntityRepository trainingEntityRepository;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    /**
     * Constructor.
     */
    public TrainingServiceImpl(TrainingEntityRepository trainingEntityRepository, TraineeService traineeService,
                               TrainerService trainerService) {
        this.trainingEntityRepository = trainingEntityRepository;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @Transactional
    @Override
    public TrainingEntity create(TrainingDto training) {
        Assert.notNull(training, "Training Dto must not be null");
        LOGGER.info("Creating a TrainingEntity according to the TrainingDto - {}", training);

        TraineeEntity trainee = traineeService.selectByUsername(training.getTraineeUsername());
        TrainerEntity trainer = trainerService.selectByUsername(training.getTrainerUsername());

        TrainingEntity createdTrainingEntity = trainingEntityRepository.save(new TrainingEntity(
            trainee,
            trainer,
            training.getTrainingName(),
            trainer.getSpecialization(),
            training.getTrainingDate(),
            training.getTrainingDuration()
        ));

        LOGGER.info(
            "Successfully created a TrainingEntity according to the TrainingEntity Create Params - {}, result - {}",
            training, createdTrainingEntity);
        return createdTrainingEntity;
    }

    @Transactional
    @Override
    public List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                                    String trainerUsername, Long trainingTypeId) {
        Assert.notNull(traineeUsername, "Trainee Username must not be null");
        LOGGER.info("Retrieving all Training Entities of a trainee({}) based on the following criteria - "
                + "from = {}, to = {}, trainerUsername = {}, trainingType = {}",
            traineeUsername, from, to, trainerUsername, trainingTypeId);

        List<TrainingEntity> all =
            trainingEntityRepository.findAllByTraineeUsernameAndCriteria(traineeUsername, from, to,
                trainerUsername, trainingTypeId);

        LOGGER.info("Successfully retrieved all Training Entities of a trainee({}) based on the following criteria - "
                + "from = {}, to = {}, trainerUsername = {}, trainingType = {}. Result of the query - {}",
            traineeUsername, from, to, trainerUsername, trainingTypeId, all);
        return all;
    }

    @Transactional
    @Override
    public List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                                    String traineeUsername) {
        Assert.notNull(trainerUsername, "Trainer Username must not be null");
        LOGGER.info("Retrieving all Training Entities of a trainer({}) based on the following criteria - "
                + "from = {}, to = {}, traineeUsername = {}",
            trainerUsername, from, to, traineeUsername);

        List<TrainingEntity> all =
            trainingEntityRepository.findAllByTrainerUsernameAndCriteria(trainerUsername, from, to,
                traineeUsername);

        LOGGER.info("Successfully retrieved all Training Entities of a trainer({}) based on the following criteria - "
                + "from = {}, to = {}, traineeUsername = {}. Result of the query - {}",
            trainerUsername, from, to, traineeUsername, all);
        return all;
    }

    @Override
    public List<TrainingEntity> findAllByTraineeUsername(String traineeUsername) {
        return this.findAllByTraineeUsernameAndCriteria(traineeUsername, null, null, null, null);
    }

    @Override
    public List<TrainingEntity> findAllByTrainerUsername(String trainerUsername) {
        return this.findAllByTrainerUsernameAndCriteria(trainerUsername, null, null, null);
    }
}
