package org.example.service.core.user;

import java.util.Optional;
import org.example.entity.UserEntity;

public interface UserService {

    UserEntity create(UserEntity user);

    UserEntity update(UserEntity user);

    boolean delete(Long id);

    UserEntity select(Long id);

    UserEntity changePassword(String username, String newPassword);

    UserEntity switchActivationState(String username, Boolean state);

    UserEntity getByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPassword(String password);

    Optional<UserEntity> findById(Long id);

    boolean usernamePasswordMatching(String username, String password);
}
