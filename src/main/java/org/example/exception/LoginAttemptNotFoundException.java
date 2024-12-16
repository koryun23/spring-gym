package org.example.exception;

public class LoginAttemptNotFoundException extends RuntimeException {

    public LoginAttemptNotFoundException(String remoteAddress) {
        super(String.format("Login attempt with a remote address %s not found", remoteAddress));
    }

}
