package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.mapper.user.UserMapper;
import org.example.service.core.AuthenticatorService;
import org.example.service.core.LoggingService;
import org.example.service.core.UserService;
import org.example.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "")
public class AuthController {

    private final LoggingService loggingService;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final AuthenticatorService authenticatorService;

    /**
     * Constructor.
     */
    public AuthController(LoggingService loggingService, UserService userService, UserMapper userMapper,
                          UserValidator userValidator,
                          AuthenticatorService authenticatorService) {
        this.loggingService = loggingService;
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.authenticatorService = authenticatorService;
    }

    /**
     * Login.
     */
    @GetMapping(value = "/login")
    public ResponseEntity<RestResponse> login(HttpServletRequest request) {
        log.info("Attempting a user log in");
        // no validations

        // logging
        loggingService.storeTransactionId();

        // service calls
        boolean userExists =
            authenticatorService.authSuccess(request.getHeader("username"), request.getHeader("password"));

        // response
        UserRetrievalResponseDto responseDto =
            new UserRetrievalResponseDto(userExists ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);

        RestResponse restResponse =
            new RestResponse(responseDto, responseDto.getHttpStatus(), LocalDateTime.now(), Collections.emptyList());
        ResponseEntity<RestResponse> responseEntity =
            new ResponseEntity<>(restResponse, restResponse.getHttpStatus());

        log.info("Response of logging in - {}", restResponse);

        loggingService.clear();
        return responseEntity;
    }

    /**
     * Change password.
     */
    @PutMapping(value = "/change-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> changePassword(
        @RequestBody UserChangePasswordRequestDto requestDto, HttpServletRequest request) {
        log.info("Attempting password change, request - {}", requestDto);

        // logging
        loggingService.storeTransactionId();

        // authentication
        if (authenticatorService.authFail(request.getHeader("username"), request.getHeader("password"))) {
            return new ResponseEntity<>(new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }

        // validations
        RestResponse restResponse = userValidator.validateChangePassword(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(userMapper.mapUserChangePasswordRequestDtoToUserEntity(requestDto));

        // response
        UserChangePasswordResponseDto responseDto = new UserChangePasswordResponseDto(HttpStatus.OK);
        restResponse = new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Password change response - {}", restResponse);

        loggingService.clear();
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
