package com.example.controller.advice;

import com.example.dto.RestResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Component
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Method for handling general exceptions.
     *
     * @param exception - Exception which was thrown in the application.
     * @return ResponseEntity containing status code and RestResponse class.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestResponse> handleGeneralException(Exception exception) {
        log.error(exception.getClass().getName());
        log.error(exception.getMessage());
        RestResponse restResponse = new RestResponse(null, HttpStatus.SERVICE_UNAVAILABLE, LocalDateTime.now(),
            List.of("Something went wrong. Try again later."));
        log.error("Rest Response - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }

    /**
     * Method for handling IllegalArgumentException, which is thrown when invalid data is provided.
     *
     * @param exception - RuntimeException which was thrown in the application.
     * @return ResponseEntity containing status code and RestResponse class
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestResponse> handleInvalidRequest(RuntimeException exception) {
        log.error("Handling invalid request");
        log.error(exception.getMessage());
        RestResponse restResponse =
            new RestResponse(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(), List.of(exception.getMessage()));
        log.error("Rest Response - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
