package org.example.service.impl;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserEntity;
import org.example.service.core.IdService;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IdServiceImpl implements IdService {

    private final UserService userService;

    public IdServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Long getId(String pattern) {
        List<String> usernames =
            userService.findAllByUsernameContains(pattern).stream().map(UserEntity::getUsername).toList();
        long maxId = Long.MIN_VALUE;
        long currentId = 0;
        for (String username : usernames) {
            String[] split = username.split("\\.");
            if (split.length == 2) {
                currentId = 1;
            } else if (split.length == 3) {
                currentId = Long.parseLong(split[2]);
            }
            if (currentId > maxId) {
                maxId = currentId;
            }
        }

        return maxId + 1;
    }
}
