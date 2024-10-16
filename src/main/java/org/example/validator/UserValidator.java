package org.example.validator;

import org.example.service.core.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

}
