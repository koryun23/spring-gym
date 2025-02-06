package org.example.validator;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.dto.request.UserChangePasswordRequestDto;
import org.example.entity.user.UserEntity;
import org.example.exception.CustomIllegalArgumentException;
import org.example.service.core.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    private UserValidator testSubject;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        testSubject = new UserValidator(userService, passwordEncoder);
    }

    @Test
    public void testValidateChangePasswordWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.validateChangePassword(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);
    }

    @Test
    public void testValidateChangePasswordWhenUsernameIsNull() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto(null, "old", "new")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }

    @Test
    public void testValidateChangePasswordWhenUsernameIsEmpty() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("", "old", "new")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }

    @Test
    public void testValidateChangePasswordWhenOldPasswordIsNull() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", null, "new")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }

    @Test
    public void testValidateChangePasswordWhenOldPasswordIsEmpty() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "", "new")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }

    @Test
    public void testValidateChangePasswordWhenNewPasswordIsNull() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "old", null)))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }

    @Test
    public void testValidateChangePasswordWhenNewPasswordIsEmpty() {
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "old", "")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoInteractions(userService, passwordEncoder);

    }


    @Test
    public void testValidateChangePasswordWhenUserDoesNotExist() {

        // when
        Mockito.when(userService.findByUsername("username")).thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(
                () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "old", "new")))
            .isExactlyInstanceOf(CustomIllegalArgumentException.class);
        Mockito.verifyNoMoreInteractions(userService);
        Mockito.verifyNoInteractions(passwordEncoder);
    }

    @Test
    public void testValidateChangePasswordWhenUsernameAndOldPasswordDoNotMatch() {

        // when
        Mockito.when(userService.findByUsername("username"))
            .thenReturn(Optional.of(new UserEntity("first", "last", "username", "old", true)));
        Mockito.when(passwordEncoder.matches("old", "old")).thenReturn(false);

        // then
        Assertions.assertThatThrownBy(
            () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "old", "new")));
        Mockito.verifyNoMoreInteractions(userService, passwordEncoder);
    }

    @Test
    public void testValidateChangePasswordWhenValid() {

        // when
        Mockito.when(userService.findByUsername("username"))
            .thenReturn(Optional.of(new UserEntity("first", "last", "username", "old", true)));
        Mockito.when(passwordEncoder.matches("old", "old")).thenReturn(true);

        // then
        Assertions.assertThatNoException().isThrownBy(
            () -> testSubject.validateChangePassword(new UserChangePasswordRequestDto("username", "old", "new")));
        Mockito.verifyNoMoreInteractions(userService, passwordEncoder);
    }
}