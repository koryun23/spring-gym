package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.facade.core.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "", consumes = "application/json", produces = "application/json")
public class AuthController {

    private UserFacade userFacade;

    public AuthController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    /**
     * Login.
     */
    @PostMapping(value = "/login")
    public ResponseEntity<RestResponse<UserRetrievalResponseDto>> login(@RequestBody UserRetrievalRequestDto requestDto,
                                                                       HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("Content-Type", "application/json");
        log.info("Attempting a user log in according to the request - {}", requestDto);
        RestResponse<UserRetrievalResponseDto> restResponse = userFacade.select(requestDto);
        ResponseEntity<RestResponse<UserRetrievalResponseDto>> responseEntity =
            new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        log.info("Successfully logged in, response - {}", restResponse);
        return responseEntity;
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<RestResponse<UserChangePasswordResponseDto>> changePassword(@RequestBody
                                                                                      UserChangePasswordRequestDto requestDto,
                                                                                      HttpServletRequest request) {
        log.info("Attempting password change, request - {}", requestDto);
        requestDto.setUsername(request.getHeader("username"));
        requestDto.setOldPassword(request.getHeader("password"));
        RestResponse<UserChangePasswordResponseDto> restResponse = userFacade.changePassword(requestDto);
        log.info("Password change response - {}", restResponse);
        ResponseEntity<RestResponse<UserChangePasswordResponseDto>> responseEntity =
            new ResponseEntity<>(restResponse, restResponse.getHttpStatus());
        return responseEntity;
    }
}
