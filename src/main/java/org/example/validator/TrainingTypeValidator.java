package org.example.validator;

import org.example.service.core.UserService;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeValidator {

    private UserService userService;

    public TrainingTypeValidator(UserService userService) {
        this.userService = userService;
    }
}
