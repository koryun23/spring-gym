package org.example.exception;

public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException(Long id) {
        super(String.format("TrainingEntity with an id of %d not found", id));
    }
}
