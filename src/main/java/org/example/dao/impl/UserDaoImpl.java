package org.example.dao.impl;

import java.util.Optional;
import org.example.dao.core.UserDao;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.repository.core.UserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserDaoImpl implements UserDao {

    private static Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    private UserEntityRepository userEntityRepository;

    public UserDaoImpl(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public UserEntity getByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving a user with a username of {}", username);
        UserEntity userEntity = userEntityRepository.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException(username));
        LOGGER.info("Successfully retrieved a user with a username of {}, result - {}", username, userEntity);
        return userEntity;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        LOGGER.info("Retrieving an optional user with a username of {}", username);
        Optional<UserEntity> optionalUser = userEntityRepository.findByUsername(username);
        LOGGER.info("Successfully retrieved an optional user with a username of {}, result - {}",
            username, optionalUser);
        return optionalUser;
    }

    @Override
    public UserEntity get(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving a user with an id of {}", id);
        UserEntity userEntity = userEntityRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        LOGGER.info("Successfully retrieved a user with an id of {}, result - {}", id, userEntity);
        return userEntity;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        Assert.notNull(userEntity, "User entity must not be null");
        LOGGER.info("Saving {}", userEntity);
        UserEntity savedUserEntity = userEntityRepository.save(userEntity);
        LOGGER.info("Saved {}, result - {}", userEntity, savedUserEntity);
        return savedUserEntity;
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        Assert.notNull(userEntity, "User entity must not be null");
        LOGGER.info("Updating a user with an id of {}", userEntity.getId());
        UserEntity updatedUserEntity = userEntityRepository.update(userEntity);
        LOGGER.info("Updated a user with an id of {}, result - {}", userEntity.getId(), updatedUserEntity);
        return updatedUserEntity;
    }

    @Override
    public boolean delete(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Deleting a user with an id of {}", id);
        userEntityRepository.deleteById(id);
        LOGGER.info("Successfully deleted a user with an id of {}", id);
        return true;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOGGER.info("Retrieving an optional user with an id of {}", id);
        Optional<UserEntity> optionalUser = userEntityRepository.findById(id);
        LOGGER.info("Successfully retrieved an optional user with an id of {}, result - {}", id, optionalUser);
        return optionalUser;
    }
}
