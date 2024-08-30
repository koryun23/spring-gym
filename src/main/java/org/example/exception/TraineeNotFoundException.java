package org.example.exception;

public class TraineeNotFoundException extends RuntimeException {

    public TraineeNotFoundException(String username) {
        super(String.format("Trainee with a username of %s not found", username));
    }

    public TraineeNotFoundException(Long id) {
        super(String.format("Trainee with an id of %d not found", id));
    }
}
