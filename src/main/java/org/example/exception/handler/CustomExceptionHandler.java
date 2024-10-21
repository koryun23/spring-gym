package org.example.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.exception.CustomIllegalArgumentException;
import org.example.exception.InvalidIdException;
import org.example.exception.TraineeNotFoundException;
import org.example.exception.TrainerNotFoundException;
import org.example.exception.TrainingNotFoundException;
import org.example.exception.TrainingTypeNotFoundException;
import org.example.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method for handling invalid request.
     */
    @ExceptionHandler({CustomIllegalArgumentException.class, InvalidIdException.class})
    public ResponseEntity<RestResponse> handleInvalidRequest(CustomIllegalArgumentException e) {
        RestResponse restResponse =
            new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(), List.of(e.getMessage()));
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Method for handling not found resources.
     */
    @ExceptionHandler({
        TraineeNotFoundException.class, TrainerNotFoundException.class, TrainingNotFoundException.class,
        TrainingTypeNotFoundException.class, UserNotFoundException.class
    })
    public ResponseEntity<RestResponse> handleResourceNotFound(RuntimeException e) {
        RestResponse restResponse =
            new RestResponse(null, HttpStatus.NOT_FOUND, LocalDateTime.now(), List.of(e.getMessage()));
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}