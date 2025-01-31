package org.example.loader;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.user.UserRoleService;
import org.example.service.core.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SuperUserLoaderImpl implements SuperUserLoader, CommandLineRunner {

    private final UserService userService;
    private final UserRoleService userRoleService;

    @Value("${security.login.superuser.username}")
    private String superUserUsername;

    @Value("${security.login.superuser.password}")
    private String superUserPassword;

    /**
     * Constructor.
     */
    public SuperUserLoaderImpl(UserService userService, UserRoleService userRoleService) {
        this.userService = userService;
        this.userRoleService = userRoleService;
    }

    @Override
    public void run(String... args) throws Exception {

        Integer count = userRoleService.countByUserRoleType(UserRoleType.SUPER_USER);
        log.info("Number of superusers - {}", count);

        if (count == 1) {
            return;
        }

        UserEntity user = userService.create(new UserEntity(
            "John", "Doe", superUserUsername, superUserPassword, true));
        userRoleService.create(new UserRoleEntity(user, UserRoleType.SUPER_USER));
    }
}
