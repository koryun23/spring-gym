package org.example.mapper.user;

import org.example.dto.plain.UserDto;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.UserEntity;

public interface UserMapper {

    UserDto mapUserEntityToUserDto(UserEntity userEntity);

    UserEntity mapUserDtoToUserEntity(UserDto userDto);
}
