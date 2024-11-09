package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.mapper.user.UserMapper;
import org.example.service.core.user.UserService;
import org.example.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping(value = "/users")
public class AuthController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    /**
     * Constructor.
     */
    public AuthController(UserService userService, UserMapper userMapper,
                          UserValidator userValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    /**
     * This method aims to enable the /login endpoint. The actual authentication logic is
     * carried out by the AuthFilter.
     */
    @GetMapping(value = "/login")
    public void login() {

    }

    /**
     * Change password.
     */
    @PutMapping(value = "/password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<RestResponse> changePassword(
            @RequestBody UserChangePasswordRequestDto requestDto, HttpServletRequest request) {
        log.info("Attempting password change, request - {}", requestDto);

        // validations
        userValidator.validateChangePassword(requestDto);

        // service and mapper calls
        userService.changePassword(requestDto.getUsername(), requestDto.getNewPassword());

        // response
        UserChangePasswordResponseDto responseDto = new UserChangePasswordResponseDto(HttpStatus.OK);
        RestResponse restResponse =
                new RestResponse(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Password change response - {}", restResponse);

        return new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
    }
}
