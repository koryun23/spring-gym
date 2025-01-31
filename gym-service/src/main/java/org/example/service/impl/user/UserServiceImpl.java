package org.example.service.impl.user;

import java.util.Optional;
import org.example.entity.user.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.repository.UserEntityRepository;
import org.example.service.core.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserEntityRepository userDao, PasswordEncoder passwordEncoder) {
        this.userEntityRepository = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserEntity create(UserEntity user) {
        Assert.notNull(user, "User Entity must not be null");
        LOGGER.info("Creating a User Entity {}", user);
        LOGGER.info("decoded password - {}", user.getPassword()); // TODO: DONT
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity savedUserEntity = userEntityRepository.save(user);
        LOGGER.info("Successfully saved {}, result - {}", user, savedUserEntity);
        return savedUserEntity;
    }

    @Transactional
    @Override
    public UserEntity update(UserEntity user) {
        Assert.notNull(user, "User Entity must not be null");
        LOGGER.info("Updating a User Entity with an id of {}", user.getId());

        String username = user.getUsername();
        userEntityRepository.update(username, user.getFirstName(), user.getLastName(), user.getIsActive());

        UserEntity updatedUserEntity = this.getByUsername(username);

        LOGGER.info("Successfully updated a User Entity with an id of {}, result - {}",
            user.getId(), updatedUserEntity);
        return updatedUserEntity;
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a User Entity with an id of {}", id);
        userEntityRepository.deleteById(id);
        LOGGER.info("Succsesfully deleted a User Entity with an id of {}", id);
        return true;
    }

    @Transactional
    @Override
    public UserEntity changePassword(String username, String newPassword) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        Assert.notNull(newPassword, "New password must not be null");
        Assert.hasText(newPassword, "New password must not be empty");
        LOGGER.info("Changing the password of the user {}", username);

        userEntityRepository.updatePassword(username, passwordEncoder.encode(newPassword));
        UserEntity userEntity = this.getByUsername(username);

        LOGGER.info("Successfully changed the password of the user {}", username);
        return userEntity;
    }

    @Transactional
    @Override
    public UserEntity switchActivationState(String username, Boolean state) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Switching the activation state of the user {}", username);

        userEntityRepository.switchActivationState(username, state);
        UserEntity updatedUserEntity = this.getByUsername(username);

        LOGGER.info("Successfully switched the activation state of the user {}, result - {}", username,
            updatedUserEntity);
        return updatedUserEntity;
    }

    @Transactional
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

    @Transactional
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

    @Transactional
    @Override
    public Optional<UserEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional User Entity with an id of {}", id);
        Optional<UserEntity> optionalUser = userEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional User Entity with an id od {}, result - {}",
            id, optionalUser);
        return optionalUser;
    }

    @Transactional
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
}

