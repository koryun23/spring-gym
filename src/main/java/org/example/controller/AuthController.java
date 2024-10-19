package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.mapper.user.UserMapper;
import org.example.service.core.AuthenticatorService;
import org.example.service.core.UserService;
import org.example.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "", consumes = "application/json", produces = "application/json")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final AuthenticatorService authenticatorService;

    /**
     * Constructor.
     */
    public AuthController(UserService userService, UserMapper userMapper, UserValidator userValidator,
                          AuthenticatorService authenticatorService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
        this.authenticatorService = authenticatorService;
    }

    /**
     * Login.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<RestResponse<UserRetrievalResponseDto>> login(
        @RequestBody UserRetrievalRequestDto requestDto) {
        log.info("Attempting a user log in according to the request - {}", requestDto);
        // no validations

        // service calls
        boolean userExists = authenticatorService.authSuccess(requestDto.getUsername(), requestDto.getPassword());

        // response
        UserRetrievalResponseDto responseDto =
            new UserRetrievalResponseDto(userExists ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        RestResponse<UserRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, responseDto.getHttpStatus(), LocalDateTime.now(), Collections.emptyList());
        ResponseEntity<RestResponse<UserRetrievalResponseDto>> responseEntity =
            new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        log.info("Response of logging in - {}", restResponse);
        return responseEntity;
    }

    /**
     * Change password.
     */
    @PutMapping(value = "/change-password")
    public ResponseEntity<RestResponse<UserChangePasswordResponseDto>> changePassword(
        @RequestBody UserChangePasswordRequestDto requestDto, HttpServletRequest request) {
        log.info("Attempting password change, request - {}", requestDto);

        // authentication
        if (authenticatorService.authFail(request.getHeader("username"), request.getHeader("password"))) {
            return new ResponseEntity<>(new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed")), HttpStatus.UNAUTHORIZED);
        }

        // validations
        RestResponse<UserChangePasswordResponseDto> restResponse = userValidator.validateChangePassword(requestDto);
        if (restResponse != null) {
            return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        }

        // service and mapper calls
        userService.update(userMapper.mapUserChangePasswordRequestDtoToUserEntity(requestDto));

        // response
        UserChangePasswordResponseDto responseDto = new UserChangePasswordResponseDto(HttpStatus.OK);
        restResponse = new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Password change response - {}", restResponse);
        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
