package org.example.dto.response;

import java.util.List;
import org.springframework.http.HttpStatus;

public class TrainerSwitchActivationStateResponseDto {

    private HttpStatus httpStatus;

    public TrainerSwitchActivationStateResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
