package org.example.service.core;

import java.util.Optional;
import org.example.entity.UserEntity;

public interface UserService {

    UserEntity create(UserEntity user);

    UserEntity update(UserEntity user);

    boolean delete(Long id);

    UserEntity select(Long id);

    UserEntity getByUsername(String username);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByPassword(String password);

    Optional<UserEntity> findById(Long id);

    boolean usernamePasswordMatching(String username, String password);
}
