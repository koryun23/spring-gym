package org.example.dto.response;

import org.springframework.http.HttpStatus;

public class TrainerSwitchActivationStateResponseDto {

    private HttpStatus httpStatus;

    public TrainerSwitchActivationStateResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
