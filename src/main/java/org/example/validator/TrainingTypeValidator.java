package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.dto.request.TrainingTypeListRetrievalRequestDto;
import org.example.dto.response.TrainingTypeListRetrievalResponseDto;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeValidator {

    private UserService userService;

    public TrainingTypeValidator(UserService userService) {
        this.userService = userService;
    }

    public RestResponse<TrainingTypeListRetrievalResponseDto> validateGetTrainingTypes(
        TrainingTypeListRetrievalRequestDto requestDto) {

        String retrieverUsername = requestDto.getRetrieverUsername();
        String retrieverPassword = requestDto.getRetrieverPassword();

        if (!userService.usernamePasswordMatching(retrieverUsername, retrieverPassword)) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        return null;
    }
}
