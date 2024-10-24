package org.example.mapper.user;

import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.UserEntity;
import org.example.service.core.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserMapperImpl implements UserMapper {

    private final UserService userService;

    public UserMapperImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserEntity mapUserChangePasswordRequestDtoToUserEntity(UserChangePasswordRequestDto requestDto) {
        Assert.notNull(requestDto, "UserChangePasswordRequestDto must not be null");

        UserEntity userEntity = userService.getByUsername(requestDto.getUsername());
        userEntity.setPassword(requestDto.getNewPassword());
        return userEntity;
    }
}
