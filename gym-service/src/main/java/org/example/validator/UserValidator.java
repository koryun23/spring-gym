package org.example.validator;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.user.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.service.core.user.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserValidator {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor.
     */
    public UserValidator(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Validate User Change Password Request Dto.
     */
    public void validateChangePassword(UserChangePasswordRequestDto requestDto) {

        if (requestDto == null) {
            throw new CustomIllegalArgumentException("Request body is missing");
        }
        log.info("Validating the operation of changing password");

        String username = requestDto.getUsername();
        if (username == null || username.isEmpty()) {
            log.error("Username is missing");
            throw new CustomIllegalArgumentException("Username is required");
        }

        String oldPassword = requestDto.getOldPassword();
        if (oldPassword == null || oldPassword.isEmpty()) {
            log.error("Old password is missing");
            throw new CustomIllegalArgumentException("Old password is required");
        }

        String newPassword = requestDto.getNewPassword();
        if (newPassword == null || newPassword.isEmpty()) {
            log.error("New password is missing");
            throw new CustomIllegalArgumentException("New password is required");
        }

        Optional<UserEntity> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()) {
            log.error("User with the provided credentials does not exist");
            throw new CustomIllegalArgumentException("User with the provided credentials does not exist");
        }

        UserEntity userEntity = optionalUser.get();
        if (!passwordEncoder.matches(oldPassword, userEntity.getPassword())) {
            log.error("User with the provided credentials does not exist");
            throw new CustomIllegalArgumentException("User with the provided credentials does not exist");
        }

        log.info("Successfully validated the operation of changing password");
    }
}
