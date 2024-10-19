package org.example.service.impl;

import org.example.service.core.AuthenticatorService;
import org.example.service.core.UserService;

public class AuthenticatorServiceImpl implements AuthenticatorService {

    private final UserService userService;

    public AuthenticatorServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean authFail(String username, String password) {
        return !userService.usernamePasswordMatching(username, password);
    }

    @Override
    public boolean authSuccess(String username, String password) {
        return userService.usernamePasswordMatching(username, password);
    }
}
