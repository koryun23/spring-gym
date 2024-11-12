package org.example.service.impl.training;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainingDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.TrainingEntityRepository;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional
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

    @Override
    public TrainingEntity create(TrainingDto training) {
        Assert.notNull(training, "Training Dto must not be null");
        LOGGER.info("Creating a TrainingEntity according to the TrainingDto - {}", training);

        TraineeEntity trainee = traineeService.selectByUsername(training.getTraineeUsername());
        List<TrainingEntity> traineeTrainings =
            this.findAllByTraineeUsernameAndCriteria(trainee.getUser().getUsername(),
                null, null, null, null);
        trainee.setTrainingEntityList(traineeTrainings);

        TrainerEntity trainer = trainerService.selectByUsername(training.getTrainerUsername());
        List<TrainingEntity> trainerTrainings =
            this.findAllByTrainerUsernameAndCriteria(trainer.getUser().getUsername(),
                null, null, null);
        trainer.setTrainingEntityList(trainerTrainings);

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

    @Override
    public TrainingEntity select(Long id) {
        Assert.notNull(id, "TrainingEntity Id must not be null");
        LOGGER.info("Selecting a TrainingEntity with an id of {}", id);
        TrainingEntity trainingEntity =
            trainingEntityRepository.findById(id).orElseThrow(() -> new TrainingNotFoundException(id));
        LOGGER.info("Successfully selected a TrainingEntity with an id of {}, result - {}", id, trainingEntity);
        return trainingEntity;
    }

    @Override
    public TrainingEntity update(TrainingEntity trainingEntity) {
        Assert.notNull(trainingEntity, "Training Entity must not be null");
        LOGGER.info("Updating a TrainingEntity with an id of {} according to {}", trainingEntity.getId(),
            trainingEntity);

        TrainingEntity updatedTrainingEntity = trainingEntityRepository.save(trainingEntity);

        TraineeEntity trainee = trainingEntity.getTrainee();
        List<TrainingEntity> traineeTrainings =
            this.findAllByTraineeUsernameAndCriteria(trainee.getUser().getUsername(),
                null, null, null, null);
        trainee.setTrainingEntityList(traineeTrainings);

        TrainerEntity trainer = trainingEntity.getTrainer();
        List<TrainingEntity> trainerTrainings =
            this.findAllByTrainerUsernameAndCriteria(trainer.getUser().getUsername(),
                null, null, null);
        trainer.setTrainingEntityList(trainerTrainings);
        LOGGER.info("Successfully updated a TrainingEntity with an id of {}, result - {}", trainingEntity.getId(),
            updatedTrainingEntity);

        return updatedTrainingEntity;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Assert.notNull(id, "TrainingEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainingEntity with an id of {}", id);
        Optional<TrainingEntity> optionalTraining = trainingEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainingEntity with an id of {}, result - {}", id,
            optionalTraining);
        return optionalTraining;
    }

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
}
