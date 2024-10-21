package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidIdException extends RuntimeException {

    public InvalidIdException(Long id) {
        super(String.format("Id must be positive: %d provided", id));
    }
}
