package org.example.exception;

public class TrainerNotFoundException extends RuntimeException {

    public TrainerNotFoundException(String username) {
        super(String.format("TrainerEntity with a username of %s not found", username));
    }

    public TrainerNotFoundException(Long id) {
        super(String.format("TrainerEntity with an id of %d not found", id));
    }
}
