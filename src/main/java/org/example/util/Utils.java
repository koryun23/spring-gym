package org.example.util;

import org.example.dto.plain.UserDto;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.TraineeEntity;
import org.example.entity.UserEntity;

public class Utils {

    public static void applyUserChangePasswordOnUserDto(UserChangePasswordRequestDto userChangePasswordRequestDto, UserDto userDto) {
        userDto.setPassword(userChangePasswordRequestDto.getNewPassword());
    }

    public static void applySwitchActivationStateOnUserDto(UserDto userDto) {
        userDto.setIsActive(userDto.getIsActive());
    }

    public static void setPasswordToUser(UserEntity userEntity, String password) {
        userEntity.setPassword(password);
    }

    public static void setUserToTrainee(TraineeEntity trainee, UserEntity user) {
        trainee.setUser(user);
    }
}
