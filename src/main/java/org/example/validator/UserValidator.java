package org.example.validator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.dto.response.UserChangePasswordResponseDto;
import org.example.entity.UserEntity;
import org.example.service.core.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private UserService userService;

    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    public RestResponse<UserChangePasswordResponseDto> validateChangePassword(UserChangePasswordRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Username is required"));
        }

        String oldPassword = requestDto.getOldPassword();
        if (oldPassword == null || oldPassword.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Old password is required"));
        }

        String newPassword = requestDto.getNewPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("New password is required"));
        }

        Optional<UserEntity> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("User with the provided credentials does not exist"));
        }

        UserEntity userEntity = optionalUser.get();
        if (!userEntity.getPassword().equals(oldPassword)) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("User with the provided credentials does not exist"));
        }

        Optional<UserEntity> optionalUserByNewPassword = userService.findByPassword(newPassword);
        if (optionalUserByNewPassword.isPresent()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Password is already occupied"));
        }

        return null;
    }

}
