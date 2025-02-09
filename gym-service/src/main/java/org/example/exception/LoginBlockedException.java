package org.example.exception;

import org.springframework.security.core.AuthenticationException;

public class LoginBlockedException extends AuthenticationException {

    public LoginBlockedException(String msg) {
        super(msg);
    }
}
