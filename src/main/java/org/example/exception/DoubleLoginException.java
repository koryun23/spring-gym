package org.example.exception;

import org.springframework.security.core.AuthenticationException;

public class DoubleLoginException extends AuthenticationException {

    public DoubleLoginException(String msg) {
        super(msg);
    }
}
