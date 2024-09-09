package org.example.exception;

public class InvalidIdException extends RuntimeException {

    public InvalidIdException(Long id) {
        super(String.format("Id must be positive: %d provided", id));
    }
}
