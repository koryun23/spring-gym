package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.facade.core.UserFacade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/login")
public class AuthController {

    private UserFacade userFacade;

    public AuthController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping
    public ResponseEntity<UserRetrievalResponseDto> login(@RequestBody UserRetrievalRequestDto requestDto,
                                                          HttpServletRequest httpServletRequest) {
        log.info("Attempting a user log in according to the request - {}", requestDto);
        UserRetrievalResponseDto responseDto = userFacade.select(requestDto);
        ResponseEntity<UserRetrievalResponseDto> responseEntity =
            new ResponseEntity<>(responseDto, HttpStatus.OK);
        log.info("Successfully logged in, response - {}", responseEntity);
        return responseEntity;
    }
}
