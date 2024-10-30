package org.example.service.impl.user;

import java.util.List;
import java.util.Optional;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.repository.UserEntityRepository;
import org.example.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserEntityRepository userEntityRepository;

    public UserServiceImpl(UserEntityRepository userDao) {
        this.userEntityRepository = userDao;
    }

    @Override
    public UserEntity create(UserEntity user) {
        Assert.notNull(user, "User Entity must not be null");
        LOGGER.info("Creating a User Entity {}", user);
        UserEntity savedUserEntity = userEntityRepository.save(user);
        LOGGER.info("Successfully saved {}, result - {}", user, savedUserEntity);
        return savedUserEntity;
    }

    @Override
    public UserEntity update(UserEntity user) {
        Assert.notNull(user, "User Entity must not be null");
        LOGGER.info("Updating a User Entity with an id of {}", user.getId());
        UserEntity updatedUserEntity = userEntityRepository.save(user);
        LOGGER.info("Successfully updated a User Entity with an id of {}, result - {}",
            user.getId(), updatedUserEntity);
        return updatedUserEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a User Entity with an id of {}", id);
        userEntityRepository.deleteById(id);
        LOGGER.info("Succsesfully deleted a User Entity with an id of {}", id);
        return true;
    }

    @Override
    public UserEntity select(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Selecting a User Entity with an id of {}", id);
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        LOGGER.info("Successfully selected a User Entity with an id of {}, result - {}", id, userEntity);
        return userEntity;
    }

    @Override
    public UserEntity getByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving a User Entity with a username of {}", username);
        UserEntity userEntity =
            userEntityRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        LOGGER.info("Successfully retrieved a User Entity with a username of {}, result - {}", username, userEntity);
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving an optional User Entity with a username of {}", username);
        Optional<UserEntity> optionalUserEntity = userEntityRepository.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional User Entity with a username of {}, result - {}",
            username, optionalUserEntity);
        return optionalUserEntity;
    }

    @Override
    public Optional<UserEntity> findByPassword(String password) {
        Assert.notNull(password, "Password must not be null");
        Assert.hasText(password, "Password must not be empty");
        LOGGER.info("Retrieving an optional user with the provided password");
        Optional<UserEntity> optionalUser = userEntityRepository.findByPassword(password);
        LOGGER.info("Successfully retrieved an optional user entity with the provided password, result - {}",
            optionalUser);
        return optionalUser;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional User Entity with an id of {}", id);
        Optional<UserEntity> optionalUser = userEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional User Entity with an id od {}, result - {}",
            id, optionalUser);
        return optionalUser;
    }

    @Override
    public boolean usernamePasswordMatching(String username, String password) {
        LOGGER.info("Checking if the given username - {}, matches the given password - {}.", username, password);

        Optional<UserEntity> optionalUser = userEntityRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return false;
        }

        UserEntity userEntity = optionalUser.get();
        return userEntity.getPassword().equals(password);
    }

    @Override
    public List<UserEntity> findAllByUsernameContains(String pattern) {
        LOGGER.info("Finding all users usernames of which contain the following pattern - {}", pattern);
        Assert.notNull(pattern, "Pattern must not be null");
        Assert.hasText(pattern, "Pattern must not be empty");
        List<UserEntity> allByUsernameContains = userEntityRepository.findAllByUsernameContains(pattern);
        LOGGER.info("Result of finding all users usernames of which contain the pattern '{}', result - {}",
            allByUsernameContains);
        return allByUsernameContains;

    }
}

