package org.example.service.impl.user;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserRoleEntity;
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
}
