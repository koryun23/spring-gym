package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User with an id of %d does not exist", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with a username of %s does not exist", username));
    }

    public UserNotFoundException(String username, String password) {
        super(String.format("User with a username of %s and password %s does not exist",
            username, "*".repeat(password.length())));
    }
}
