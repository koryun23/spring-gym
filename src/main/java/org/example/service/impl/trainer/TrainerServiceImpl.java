package org.example.service.impl.trainer;

import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.entity.TrainerEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.TrainerEntityRepository;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingTypeService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Transactional
@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final UsernamePasswordService usernamePasswordService;
    private final TrainerEntityRepository trainerDao;
    private final UserService userService;
    private final TrainingTypeService trainingTypeService;

    /**
     * Constructor.
     */
    public TrainerServiceImpl(UsernamePasswordService usernamePasswordService, TrainerEntityRepository trainerDao,
                              UserService userService, TrainingTypeService trainingTypeService) {
        this.usernamePasswordService = usernamePasswordService;
        this.trainerDao = trainerDao;
        this.userService = userService;
        this.trainingTypeService = trainingTypeService;
    }

    @Override
    public TrainerEntity create(TrainerDto trainer) {
        Assert.notNull(trainer, "TrainerCreateParams must not be null");
        LOGGER.info("Creating a TrainerEntity based on TrainerCreateParams - {}", trainer);

        UserDto userDto = trainer.getUserDto();
        UserEntity userEntity = new UserEntity(
            userDto.getFirstName(),
            userDto.getLastName(),
            usernamePasswordService.username(userDto.getFirstName(), userDto.getLastName()),
            usernamePasswordService.password(),
            userDto.getIsActive()
        );
        userService.create(userEntity);

        TrainerEntity createdTrainerEntity =
            trainerDao.save(new TrainerEntity(userEntity, trainingTypeService.get(trainer.getSpecializationId())));
        LOGGER.info("Successfully created a TrainerEntity based on TrainerCreateParams - {}, result - {}",
            trainer,
            createdTrainerEntity);
        return createdTrainerEntity;
    }

    @Override
    public TrainerEntity update(TrainerDto trainer) {
        Assert.notNull(trainer, "TrainerUpdateParams must not be null");
        LOGGER.info("Updating a TrainerEntity based on TrainerUpdateParams - {}", trainer);

        UserEntity updatedUserEntity = userService.update(new UserEntity(
            trainer.getUserDto().getFirstName(),
            trainer.getUserDto().getLastName(),
            trainer.getUserDto().getUsername(),
            null,
            trainer.getUserDto().getIsActive()
        ));
        TrainerEntity persistedTrainer = this.selectByUsername(trainer.getUserDto().getUsername());
        persistedTrainer.setUser(updatedUserEntity);
        persistedTrainer.setSpecialization(trainingTypeService.get(trainer.getSpecializationId()));
        TrainerEntity updatedTrainerEntity = trainerDao.save(persistedTrainer);
        LOGGER.info("Successfully updated a Trainer based on TrainerUpdateParams - {}, result - {}",
            trainer,
            updatedTrainerEntity);
        return updatedTrainerEntity;
    }

    @Override
    public TrainerEntity select(Long trainerId) {
        Assert.notNull(trainerId, "TrainerEntity id must not be null");
        LOGGER.info("Selecting a TrainerEntity with an id of {}", trainerId);
        TrainerEntity trainerEntity =
            trainerDao.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException(trainerId));
        LOGGER.info("Successfully selected a TrainerEntity with an id of {}, result - {}", trainerId, trainerEntity);
        return trainerEntity;
    }

    @Override
    public TrainerEntity selectByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Selecting a TrainerEntity with a username of {}", username);
        TrainerEntity trainerEntity =
            trainerDao.findByUserUsername(username).orElseThrow(() -> new TrainerNotFoundException(username));
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
        LOGGER.info("Retrieving an optional TrainerEntity with a username of {}", username);
        Optional<TrainerEntity> optionalTrainer = trainerDao.findByUserUsername(username);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with a username of {}, result - {}", username,
            optionalTrainer);
        return optionalTrainer;
    }

    @Override
    public List<TrainerEntity> findAllNotAssignedTo(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all trainers not assigned to trainee with a username of {}", traineeUsername);
        List<TrainerEntity> all = trainerDao.findAllTrainersNotAssignedTo(traineeUsername);

        LOGGER.info("Successfully retrieved all trainers not assigned to trainee with a username of {}, result - {}",
            traineeUsername, all);
        return all;
    }
}
