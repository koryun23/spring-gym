package org.example.service.impl;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.example.entity.user.UserEntity;
import org.example.repository.UserEntityRepository;
import org.example.service.core.user.UserService;
import org.example.service.impl.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService testSubject;

    @Mock
    private UserEntityRepository userEntityRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        testSubject = new UserServiceImpl(userEntityRepository, passwordEncoder);
    }

    @Test
    public void testCreateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.create(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testCreate() {
        Mockito.when(userEntityRepository.save(new UserEntity(
            "first", "last", "username", "encoded", true
        ))).thenReturn(new UserEntity(
            "first", "last", "username", "encoded", true
        ));
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encoded");

        Assertions.assertThat(testSubject.create(new UserEntity(
            "first", "last", "username", "password", true
        ))).isEqualTo(new UserEntity(
            "first", "last", "username", "encoded", true
        ));

        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testUpdateWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.update(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    // TODO
    @Test
    public void testUpdate() {

    }

    @Test
    public void testDeleteWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.delete(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelectWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.select(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testSelect() {
        Mockito.when(userEntityRepository.findById(1L)).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.select(1L)).isEqualTo(new UserEntity(
            "first", "last", "username", "password", true
        ));
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testGetByUsernameWhenNull() {
        Assertions.assertThatThrownBy(() -> testSubject.getByUsername(null))
            .isExactlyInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void testGetByUsername() {
        Mockito.when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.getByUsername("username")).isEqualTo(new UserEntity(
            "first", "last", "username", "password", true
        ));
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testFindByUsername() {
        Mockito.when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.findByUsername("username")).isEqualTo(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testFindByPassword() {
        Mockito.when(userEntityRepository.findByPassword("password")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.findByPassword("password")).isEqualTo(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testFindById() {
        Mockito.when(userEntityRepository.findById(1L)).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.findById(1L)).isEqualTo(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testUsernamePasswordMatchingWhenMatch() {
        Mockito.when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.usernamePasswordMatching("username", "password")).isEqualTo(true);
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }

    @Test
    public void testUsernamePasswordMatchingWhenDoNotMatch() {
        Mockito.when(userEntityRepository.findByUsername("username")).thenReturn(Optional.of(new UserEntity(
            "first", "last", "username", "password", true
        )));
        Assertions.assertThat(testSubject.usernamePasswordMatching("username", "password1")).isEqualTo(false);
        Mockito.verifyNoMoreInteractions(userEntityRepository);
    }
}