package org.example.facade.impl;

import java.time.LocalDateTime;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.facade.core.UserFacade;
import org.example.mapper.user.UserMapper;
import org.example.service.core.UserService;
import org.example.validator.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    public UserFacadeImpl(UserService userService, UserMapper userMapper, UserValidator userValidator) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    @Override
    public RestResponse<UserRetrievalResponseDto> select(UserRetrievalRequestDto requestDto) {
        Assert.notNull(requestDto, "User Retrieval Request Dto must not be null");
        log.info("Selecting a user based on UserRetrievalRequestDto - {}", requestDto);

        // no validations

        // service calls
        boolean userExists = userService.usernamePasswordMatching(requestDto.getUsername(), requestDto.getPassword());

        // response
        UserRetrievalResponseDto responseDto = new UserRetrievalResponseDto(
            userExists ? HttpStatus.OK : HttpStatus.NOT_FOUND);

        RestResponse<UserRetrievalResponseDto> restResponse =
            new RestResponse<>(responseDto, responseDto.getHttpStatus(), LocalDateTime.now(), Collections.emptyList());

        log.info("UserRetrievalRequestDto - {}, result - {}", requestDto,
            restResponse);
        return restResponse;
    }

    @Override
    public RestResponse<UserChangePasswordResponseDto> changePassword(UserChangePasswordRequestDto requestDto) {
        Assert.notNull(requestDto, "UserChangePasswordRequestDto must not be null");
        log.info("Changing password according to the UserChangePasswordRequestDto - {}", requestDto);

        // validations
        RestResponse<UserChangePasswordResponseDto> restResponse = userValidator.validateChangePassword(requestDto);
        if (restResponse != null) {
            return restResponse;
        }

        // service and mapper calls
        userService.update(userMapper.mapUserChangePasswordRequestDtoToUserEntity(requestDto));

        // response
        UserChangePasswordResponseDto responseDto = new UserChangePasswordResponseDto(HttpStatus.OK);
        restResponse =
            new RestResponse<>(responseDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        log.info("Successfully changed password according to the UserChangePasswordRequestDto - {}, result - {}",
            responseDto, requestDto);
        return restResponse;
    }
}
