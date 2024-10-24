package org.example.service.impl.user;

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserEntity;
import org.example.service.core.user.IdService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class UsernamePasswordServiceImpl implements UsernamePasswordService {

    private final UserService userService;
    private final IdService idService;

    /**
     * Constructor.
     */
    public UsernamePasswordServiceImpl(UserService userService, IdService idService) {
        this.userService = userService;
        this.idService = idService;
    }

    @Override
    public String username(String firstName, String lastName) {
        Assert.notNull(firstName, "First Name must not be null");
        Assert.hasText(firstName, "First name must not be empty");
        Assert.notNull(lastName, "Last Name must not be null");
        Assert.hasText(lastName, "Last Name must not be empty");

        String temporaryUsername = firstName + "." + lastName;
        Optional<UserEntity> optionalUser = userService.findByUsername(temporaryUsername);

        if (optionalUser.isEmpty()) {
            return temporaryUsername;
        }

        return temporaryUsername + ("." + idService.getId(temporaryUsername));
    }

    @Override
    public String password() {
        return UUID.randomUUID().toString().substring(0, 10);
    }
}
