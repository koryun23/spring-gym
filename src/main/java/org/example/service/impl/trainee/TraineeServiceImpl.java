package org.example.service.impl.trainee;

import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.TraineeEntityRepository;
import org.example.service.core.trainee.TraineeService;
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
    private final UsernamePasswordService usernamePasswordService;

    /**
     * Constructor.
     */
    public TraineeServiceImpl(TraineeEntityRepository traineeDao, UserService userService,
                              UsernamePasswordService usernamePasswordService) {
        this.traineeDao = traineeDao;
        this.userService = userService;
        this.usernamePasswordService = usernamePasswordService;
    }

    @Transactional
    @Override
    public TraineeEntity create(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Creating a TraineeEntity based on TraineeCreateParams - {}", trainee);

        UserEntity user = trainee.getUser();
        user.setUsername(usernamePasswordService.username(user.getFirstName(), user.getLastName()));
        user.setPassword(usernamePasswordService.password());

        userService.create(user);
        TraineeEntity createdTrainee = traineeDao.save(trainee);

        LOGGER.info("Successfully created a TraineeEntity based on TraineeCreateParams - {}, result - {}", trainee,
            trainee);
        return createdTrainee;
    }

    @Transactional
    @Override
    public TraineeEntity update(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeCreateParams must not be null");
        LOGGER.info("Updating a TraineeEntity based on TraineeUpdateParams - {}", trainee);

        UserEntity persistedUser = userService.update(trainee.getUser());

        TraineeEntity updatedTrainee = this.selectByUsername(trainee.getUser().getUsername());
        updatedTrainee.setUser(persistedUser);
        updatedTrainee.setAddress(trainee.getAddress());
        updatedTrainee.setDateOfBirth(trainee.getDateOfBirth());
        traineeDao.save(updatedTrainee);

        LOGGER.info("Successfully updated a TraineeEntity based on TraineeUpdateParams - {}, result - {}", trainee,
            trainee);
        return updatedTrainee;
    }

    @Transactional
    @Override
    public boolean delete(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.notNull(username, "Username must not be empty");
        LOGGER.info("Deleting a Trainee with a username of {}", username);

        traineeDao.deleteByUserUsername(username);

        LOGGER.info("Successfully deleted a Trainee with a username of {}", username);
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
        LOGGER.info("Selecting a TraineeEntity with a username of {}", username);

        TraineeEntity trainee =
            traineeDao.findByUserUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));

        LOGGER.info("Successfully selected a trainee with a username of {}, result - {}", username, trainee);
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
        LOGGER.info("Retrieving an optional TraineeEntity with a username of {}", username);
        Optional<TraineeEntity> optionalTrainee = traineeDao.findByUserUsername(username);
        LOGGER.info("Successfully retrieved an optional TraineeEntity with an username of {}, result - {}", username,
            optionalTrainee);
        return optionalTrainee;
    }
}
