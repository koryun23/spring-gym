package org.example.facade.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.request.UserRetrievalRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.dto.response.UserRetrievalResponseDto;
import org.example.entity.UserEntity;
import org.example.facade.core.UserFacade;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@Slf4j
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    public UserFacadeImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserRetrievalResponseDto select(UserRetrievalRequestDto requestDto) {
        Assert.notNull(requestDto, "User Retrieval Request Dto must not be null");
        log.info("Selecting a user based on UserRetrievalRequestDto - {}", requestDto);

        Optional<UserEntity> optionalUser = userService.findByUsername(requestDto.getUsername());
        UserRetrievalResponseDto responseDto = new UserRetrievalResponseDto(
            optionalUser.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);

        log.info("UserRetrievalRequestDto - {}, result - {}", requestDto,
            responseDto);
        return responseDto;
    }

    @Override
    public UserChangePasswordResponseDto changePassword(UserChangePasswordRequestDto requestDto) {
        Assert.notNull(requestDto, "UserChangePasswordRequestDto must not be null");
        log.info("Changing password according to the UserChangePasswordRequestDto - {}", requestDto);

        if (!userService.usernamePasswordMatching(requestDto.getUsername(), requestDto.getOldPassword())) {
            return new UserChangePasswordResponseDto(HttpStatus.FORBIDDEN);
        }

        UserEntity userEntity = userService.getByUsername(requestDto.getUsername());
        userEntity.setPassword(requestDto.getNewPassword());
        userService.update(userEntity);

        UserChangePasswordResponseDto responseDto = new UserChangePasswordResponseDto(HttpStatus.OK);

        log.info("Successfully changed password according to the UserChangePasswordRequestDto - {}, result - {}",
            responseDto, requestDto);
        return responseDto;
    }
}
