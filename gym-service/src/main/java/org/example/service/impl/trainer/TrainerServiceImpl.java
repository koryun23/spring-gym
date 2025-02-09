package org.example.service.impl.trainer;

import java.util.List;
import java.util.Optional;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.entity.trainer.TrainerEntity;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.TrainerEntityRepository;
import org.example.service.core.trainer.TrainerService;
import org.example.service.core.training.TrainingTypeService;
import org.example.service.core.user.UserRoleService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TrainerServiceImpl implements TrainerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private final UsernamePasswordService usernamePasswordService;
    private final TrainerEntityRepository trainerDao;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final TrainingTypeService trainingTypeService;

    /**
     * Constructor.
     */
    public TrainerServiceImpl(UsernamePasswordService usernamePasswordService, TrainerEntityRepository trainerDao,
                              UserService userService, UserRoleService userRoleService,
                              TrainingTypeService trainingTypeService) {
        this.usernamePasswordService = usernamePasswordService;
        this.trainerDao = trainerDao;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.trainingTypeService = trainingTypeService;
    }

    @Transactional
    @Override
    public TrainerDto create(TrainerDto trainer) {
        Assert.notNull(trainer, "TrainerCreateParams must not be null");
        LOGGER.info("Creating a TrainerEntity based on TrainerCreateParams - {}", trainer);

        UserDto userDto = trainer.getUserDto();
        String username = usernamePasswordService.username(userDto.getFirstName(), userDto.getLastName());
        String password = usernamePasswordService.password();
        LOGGER.info("username - {}, password - {}", username, password);

        userDto.setUsername(username);
        userDto.setPassword(password);
        trainer.setUserDto(userDto);
        UserEntity userEntity = new UserEntity(
            userDto.getFirstName(),
            userDto.getLastName(),
            username,
            password,
            userDto.getIsActive()
        );
        userService.create(userEntity);
        userRoleService.create(new UserRoleEntity(userEntity, UserRoleType.TRAINER));

        TrainerEntity createdTrainerEntity =
            trainerDao.save(new TrainerEntity(userEntity, trainingTypeService.get(trainer.getSpecializationId())));

        LOGGER.info("Successfully created a TrainerEntity result - {}", trainer);
        return trainer;
    }

    @Transactional
    @Override
    public TrainerEntity update(TrainerDto trainer) {
        Assert.notNull(trainer, "TrainerUpdateParams must not be null");
        LOGGER.info("Updating a TrainerEntity based on TrainerUpdateParams - {}", trainer);

        String username = trainer.getUserDto().getUsername();

        userService.update(new UserEntity(
            trainer.getUserDto().getFirstName(),
            trainer.getUserDto().getLastName(),
            username,
            null,
            trainer.getUserDto().getIsActive()
        ));
        trainerDao.update(username, trainingTypeService.get(trainer.getSpecializationId()));
        TrainerEntity updatedTrainerEntity = this.selectByUsername(username);

        LOGGER.info("Successfully updated a Trainer based on TrainerUpdateParams - {}, result - {}",
            trainer,
            updatedTrainerEntity);
        return updatedTrainerEntity;
    }

    @Transactional
    @Override
    public TrainerEntity select(Long trainerId) {
        Assert.notNull(trainerId, "TrainerEntity id must not be null");
        LOGGER.info("Selecting a TrainerEntity with an id of {}", trainerId);
        TrainerEntity trainerEntity =
            trainerDao.findById(trainerId).orElseThrow(() -> new TrainerNotFoundException(trainerId));
        LOGGER.info("Successfully selected a TrainerEntity with an id of {}, result - {}", trainerId, trainerEntity);
        return trainerEntity;
    }

    @Transactional
    @Override
    public TrainerEntity selectByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Selecting a TrainerEntity with the given username");
        TrainerEntity trainerEntity =
            trainerDao.findByUserUsername(username).orElseThrow(() -> new TrainerNotFoundException(username));
        LOGGER.info("Successfully selected a TrainerEntity with the given username, result - {}", trainerEntity);
        return trainerEntity;
    }

    @Transactional
    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Assert.notNull(id, "TrainerEntity id must not be null");
        LOGGER.info("Retrieving an optional TrainerEntity with an id of {}", id);
        Optional<TrainerEntity> optionalTrainer = trainerDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with an id of {}, result - {}", id,
            optionalTrainer);
        return optionalTrainer;
    }

    @Transactional
    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Assert.notNull(username, "TrainerEntity username must not be null");
        Assert.hasText(username, "TrainerEntity username must not be empty");
        LOGGER.info("Retrieving an optional TrainerEntity with the given username");
        Optional<TrainerEntity> optionalTrainer = trainerDao.findByUserUsername(username);
        LOGGER.info("Successfully retrieved an optional TrainerEntity with the given username, result - {}",
            optionalTrainer);
        return optionalTrainer;
    }

    @Transactional
    @Override
    public List<TrainerEntity> findAllNotAssignedTo(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee username must not be null");
        Assert.hasText(traineeUsername, "Trainee username must not be empty");
        LOGGER.info("Retrieving all trainers not assigned to trainee with the given username");
        List<TrainerEntity> all = trainerDao.findAllTrainersNotAssignedTo(traineeUsername);

        LOGGER.info("Successfully retrieved all trainers not assigned to trainee with the given username, result - {}",
            all);
        return all;
    }
}
