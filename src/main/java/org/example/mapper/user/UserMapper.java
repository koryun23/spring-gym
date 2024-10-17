package org.example.mapper.user;

import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.UserEntity;

public interface UserMapper {

    UserEntity mapUserChangePasswordRequestDtoToUserEntity(UserChangePasswordRequestDto requestDto);
}
