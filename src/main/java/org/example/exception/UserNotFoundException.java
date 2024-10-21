package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User with an id of %d not found", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with a username of %s not found", username));
    }
}
