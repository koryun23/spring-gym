package org.example.exception;

public class AuthenticationFailureException extends RuntimeException {

    public AuthenticationFailureException(String msg) {
        super(msg);
    }
}
