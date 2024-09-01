package org.example.exception;

public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException(Long id) {
        super(String.format("Training with an id of %d not found", id));
    }
}
