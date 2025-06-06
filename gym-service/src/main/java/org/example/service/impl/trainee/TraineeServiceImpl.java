package org.example.service.impl.trainee;

import java.util.Optional;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.UserDto;
import org.example.entity.trainee.TraineeEntity;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.TraineeEntityRepository;
import org.example.service.core.trainee.TraineeService;
import org.example.service.core.user.UserRoleService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TraineeServiceImpl implements TraineeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final TraineeEntityRepository traineeDao;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final UsernamePasswordService usernamePasswordService;

    /**
     * Constructor.
     */
    public TraineeServiceImpl(TraineeEntityRepository traineeDao, UserService userService,
                              UserRoleService userRoleService,
                              UsernamePasswordService usernamePasswordService) {
        this.traineeDao = traineeDao;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.usernamePasswordService = usernamePasswordService;
    }

    @Transactional
    @Override
    public TraineeDto create(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Creating a TraineeEntity based on TraineeCreateParams - {}", trainee);

        UserEntity user = trainee.getUser();
        String username = usernamePasswordService.username(user.getFirstName(), user.getLastName());
        String password = usernamePasswordService.password();

        user.setUsername(username);
        user.setPassword(password);

        userService.create(user);
        userRoleService.create(new UserRoleEntity(user, UserRoleType.TRAINEE));

        TraineeEntity createdTrainee = traineeDao.save(trainee);
        TraineeDto traineeDto = new TraineeDto(
            new UserDto(
                user.getFirstName(),
                user.getLastName(),
                username,
                password,
                user.getIsActive()
            ),
            createdTrainee.getDateOfBirth(),
            createdTrainee.getAddress()
        );
        LOGGER.info("Successfully created a TraineeEntity based on TraineeCreateParams - {}, result - {}", trainee,
            traineeDto);
        return traineeDto;
    }

    @Transactional
    @Override
    public TraineeEntity update(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Updating a TraineeEntity based on TraineeUpdateParams - {}", trainee);

        UserEntity persistedUser = userService.update(trainee.getUser());
        traineeDao.update(trainee.getUser().getUsername(), trainee.getDateOfBirth(), trainee.getAddress());
        TraineeEntity updatedTrainee = this.selectByUsername(trainee.getUser().getUsername());

        LOGGER.info("Successfully updated a TraineeEntity based on TraineeUpdateParams - {}, result - {}", trainee,
            trainee);
        return updatedTrainee;
    }

    @Transactional
    @Override
    public boolean delete(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(username, "Username must not be empty");
        LOGGER.info("Deleting a Trainee with the given username");

        traineeDao.deleteByUserUsername(username);

        LOGGER.info("Successfully deleted a Trainee with the given username");
        return true;
    }

    @Transactional
    @Override
    public TraineeEntity select(Long traineeId) {
        Assert.notNull(traineeId, "TraineeEntity id must not be null");
        LOGGER.info("Selecting a TraineeEntity with an id of {}", traineeId);
        TraineeEntity trainee =
            traineeDao.findById(traineeId).orElseThrow(() -> new TraineeNotFoundException(traineeId));
        LOGGER.info("Successfully selected a TraineeEntity with an id of {}, result - {}", traineeId, trainee);
        return trainee;
    }

    @Transactional
    @Override
    public TraineeEntity selectByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Selecting a TraineeEntity with the given username");

        TraineeEntity trainee =
            traineeDao.findByUserUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));

        LOGGER.info("Successfully selected a trainee with the given username, result - {}", trainee);
        return trainee;
    }

    @Transactional
    @Override
    public Optional<TraineeEntity> findById(Long id) {
        Assert.notNull(id, "TraineeEntity id must not be null");
        LOGGER.info("Retrieved an optional TraineeEntity with an id of {}", id);
        Optional<TraineeEntity> optionalTrainee = traineeDao.findById(id);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with an id of {}, result - {}", id,
            optionalTrainee);
        return optionalTrainee;
    }

    @Transactional
    @Override
    public Optional<TraineeEntity> findByUsername(String username) {
        Assert.notNull(username, "TraineeEntity username must not be null");
        Assert.hasText(username, "TraineeEntity username must not be empty");
        LOGGER.info("Retrieving an optional TraineeEntity with the given username");
        Optional<TraineeEntity> optionalTrainee = traineeDao.findByUserUsername(username);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with the given username, result - {}",
            optionalTrainee);
        return optionalTrainee;
    }
}
