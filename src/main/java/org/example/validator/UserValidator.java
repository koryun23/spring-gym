package org.example.validator;

import java.util.Optional;
import org.example.dto.RestResponse;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private UserService userService;

    /**
     * Constructor.
     */
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    /**
     * Validate User Change Password Request Dto.
     */
    public RestResponse validateChangePassword(UserChangePasswordRequestDto requestDto) {

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            throw new CustomIllegalArgumentException("Username is required");
        }

        String oldPassword = requestDto.getOldPassword();
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new CustomIllegalArgumentException("Old password is required");
        }

        String newPassword = requestDto.getNewPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            throw new CustomIllegalArgumentException("New password is required");
        }

        Optional<UserEntity> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomIllegalArgumentException("User with the provided credentials does not exist");
        }

        UserEntity userEntity = optionalUser.get();
        if (!userEntity.getPassword().equals(oldPassword)) {
            throw new CustomIllegalArgumentException("User with the provided credentials does not exist");
        }

        Optional<UserEntity> optionalUserByNewPassword = userService.findByPassword(newPassword);
        if (optionalUserByNewPassword.isPresent()) {
            throw new CustomIllegalArgumentException("Password is already occupied");
        }

        return null;
    }

}
