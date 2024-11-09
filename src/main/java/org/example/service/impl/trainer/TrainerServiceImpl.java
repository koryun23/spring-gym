package org.example.service.impl.trainer;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.example.entity.UserEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.TrainerEntityRepository;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final UsernamePasswordService usernamePasswordService;
    private final TrainerEntityRepository trainerDao;
    private final UserService userService;

    /**
     * Constructor.
     */
    public TrainerServiceImpl(UsernamePasswordService usernamePasswordService, TrainerEntityRepository trainerDao,
                              UserService userService) {
        this.usernamePasswordService = usernamePasswordService;
        this.trainerDao = trainerDao;
        this.userService = userService;
    }

    @Override
    public TrainerEntity create(TrainerEntity trainerEntity) {
        Assert.notNull(trainerEntity, "TrainerCreateParams must not be null");
        LOGGER.info("Creating a TrainerEntity based on TrainerCreateParams - {}", trainerEntity);

        UserEntity user = trainerEntity.getUser();
        user.setUsername(usernamePasswordService.username(user.getFirstName(), user.getLastName()));
        user.setPassword(usernamePasswordService.password());
        userService.create(user);
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
        TrainerEntity updatedTrainerEntity = trainerDao.update(
            trainerEntity.getUser().getUsername(), trainerEntity.getSpecialization().getTrainingType()
        );
        LOGGER.info("Successfully updated a TrainerEntity based on TrainerUpdateParams - {}, result - {}",
            trainerEntity,
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
