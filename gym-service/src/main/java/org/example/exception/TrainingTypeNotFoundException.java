package org.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TrainingTypeNotFoundException extends RuntimeException {

    public TrainingTypeNotFoundException(Long id) {
        super(String.format("Training type with an id of %d not found", id));
    }

    public TrainingTypeNotFoundException(String trainingType) {
        super(String.format("Training type '{}' not found", trainingType));
    }
}
