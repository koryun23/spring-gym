package org.example.dto.response;

import java.util.List;
import org.springframework.http.HttpStatus;

public class TrainerSwitchActivationStateResponseDto {

    private HttpStatus httpStatus;

    private List<String> errors;

    public TrainerSwitchActivationStateResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public TrainerSwitchActivationStateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
