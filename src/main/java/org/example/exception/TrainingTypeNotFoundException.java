package org.example.exception;

import org.example.entity.TrainingType;

public class TrainingTypeNotFoundException extends RuntimeException {

    public TrainingTypeNotFoundException(Long id) {
        super(String.format("Training type with an id of %d not found", id));
    }

    public TrainingTypeNotFoundException(String trainingType) {
        super(String.format("Training type '{}' not found", trainingType));
    }
}
