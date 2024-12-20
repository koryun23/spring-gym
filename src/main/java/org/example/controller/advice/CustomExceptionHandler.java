package org.example.controller.advice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method for handling invalid request.
     */
    @ExceptionHandler({CustomIllegalArgumentException.class, InvalidIdException.class, IllegalArgumentException.class})
    public ResponseEntity<RestResponse> handleInvalidRequest(RuntimeException e) {
        log.info("Handling invalid request");
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

    /**
     * Method for handling access denied exception.
     */
    @ExceptionHandler({AuthorizationDeniedException.class})
    public ResponseEntity<RestResponse> handleAccessDeniedException(AuthorizationDeniedException accessDeniedException) {
        RestResponse restResponse =
            new RestResponse(null, HttpStatus.FORBIDDEN, LocalDateTime.now(), List.of("Access Denied"));
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Method for handling general exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleGeneralException(Exception exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        RestResponse restResponse = new RestResponse(null, HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now(),
            List.of("Something went wrong"));
        return new ResponseEntity<>(
            restResponse,
            restResponse.getHttpStatus()
        );
    }
}
