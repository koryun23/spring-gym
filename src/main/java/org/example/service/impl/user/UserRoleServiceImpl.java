package org.example.service.impl.user;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.repository.UserRoleEntityRepository;
import org.example.service.core.user.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
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
    public Integer countByUserRoleType(UserRoleType userRoleType) {
        Assert.notNull(userRoleType, "User role type must not be null");
        log.info("Retrieving the number of users having the role {}", userRoleType);

        Integer count = userRoleEntityRepository.countOfRole(userRoleType);

        log.info("The number of users having the role {} - {}", userRoleType, count);

        return count;
    }
}
