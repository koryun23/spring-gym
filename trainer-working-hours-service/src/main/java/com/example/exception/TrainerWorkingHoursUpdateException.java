package com.example.exception;

public class TrainerWorkingHoursUpdateException extends RuntimeException {

    public TrainerWorkingHoursUpdateException(String msg) {
        super(msg);
    }

    public TrainerWorkingHoursUpdateException(Long id) {
        super(String.format("Failed to update working hours of a trainer with id of %d", id));
    }
}
