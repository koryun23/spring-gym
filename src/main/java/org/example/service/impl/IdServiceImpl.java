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
        log.info(usernames.size() + "");
        long maxId = Long.MIN_VALUE;
        long currentId = 0;
        for (String username : usernames) {
            String[] split = username.split("\\.");
            log.info("username - {}, split - {}", username, Arrays.toString(split));
            if (split.length == 2) {
                currentId = 1;
            } else if (split.length == 3) {
                currentId = Long.parseLong(split[2]);
            }
            if (currentId > maxId) {
                maxId = currentId;
            }
            log.info("current id - {}", currentId);
        }

        return maxId + 1;
    }
}
