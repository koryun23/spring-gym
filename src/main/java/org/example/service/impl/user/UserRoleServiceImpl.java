package org.example.service.impl.user;

import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.example.entity.user.UserRoleEntity;
import org.example.repository.UserRoleEntityRepository;
import org.example.service.core.user.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Log4j2
@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleEntityRepository userRoleEntityRepository;

    public UserRoleServiceImpl(UserRoleEntityRepository userRoleEntityRepository) {
        this.userRoleEntityRepository = userRoleEntityRepository;
    }

    @Override
    public UserRoleEntity create(UserRoleEntity userRoleEntity) {
        Assert.notNull(userRoleEntity, "User Role Entity must not be null");
        log.info("Creating a new UserRoleEntity - {}", userRoleEntity);

        UserRoleEntity savedUserRoleEntity = userRoleEntityRepository.save(userRoleEntity);

        log.info("Successfully created a new UserRoleEntity - {}", savedUserRoleEntity);
        return savedUserRoleEntity;
    }

    @Override
    public List<UserRoleEntity> findAllByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        log.info("Getting all UserRoleEntity of user with a username of {}", username);

        List<UserRoleEntity> all = userRoleEntityRepository.findAllByUserUsername(username);

        log.info("Successfully retrieved all UserRoleEntities of a user with a username of {}, result - {}", username,
            all);
        return all;
    }

    @Override
    public void deleteByUsername(String username) {
        Assert.notNull(username, "Username must not be null");
        Assert.hasText(username, "Username must not be empty");
        log.info("Deleting all UserRoleEntities of a user with a username of {}", username);
        userRoleEntityRepository.deleteAllByUserUsername(username);
        log.info("Successfully deleted all UserRoleEntities of a user with a username of {}", username);
    }
}
