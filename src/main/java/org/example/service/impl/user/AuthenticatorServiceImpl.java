package org.example.service.impl.user;

import org.example.service.core.user.AuthenticatorService;
import org.example.service.core.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatorServiceImpl implements AuthenticatorService {

    private final UserService userService;

    public AuthenticatorServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authFail(String username, String password) {
        return !userService.usernamePasswordMatching(username, password);
    }
}
