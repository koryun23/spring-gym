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
        if (!userService.usernamePasswordMatching(requestDto.getUsername(), requestDto.getOldPassword())) {
            return new RestResponse<>(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed"));
        }

        String newPassword = requestDto.getNewPassword();
        Optional<UserEntity> optionalUserByPassword = userService.findByPassword(newPassword);
        if (optionalUserByPassword.isPresent()) {
            return new RestResponse<>(null, HttpStatus.NOT_ACCEPTABLE, LocalDateTime.now(),
                List.of("Password is already occupied"));
        }
        return null;
    }

}
