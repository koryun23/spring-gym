package org.example.repository.core;

import java.util.Optional;
import org.example.entity.UserEntity;

public interface UserEntityRepository {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findById(Long id);

    void deleteById(Long id);

    UserEntity save(UserEntity user);

}
