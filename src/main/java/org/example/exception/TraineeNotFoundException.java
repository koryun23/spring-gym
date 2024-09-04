package org.example.exception;

public class TraineeNotFoundException extends RuntimeException {

    public TraineeNotFoundException(String username) {
        super(String.format("TraineeEntity with a username of %s not found", username));
    }

    public TraineeNotFoundException(Long id) {
        super(String.format("TraineeEntity with an id of %d not found", id));
    }
}
