package org.example.mapper.user;

import org.example.dto.plain.UserDto;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.UserEntity;
import org.example.service.core.user.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto mapUserEntityToUserDto(UserEntity userEntity) {
        return new UserDto(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getIsActive()
        );
    }

    @Override
    public UserEntity mapUserDtoToUserEntity(UserDto userDto) {
        return new UserEntity(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getUsername(),
                userDto.getPassword(),
                userDto.getIsActive()
        );
    }
}
