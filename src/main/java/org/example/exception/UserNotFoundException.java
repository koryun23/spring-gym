package org.example.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User with an id of %d not found", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with a username of %s not found", username));
    }
}
