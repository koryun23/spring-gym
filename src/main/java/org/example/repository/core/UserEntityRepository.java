package org.example.repository.core;

import java.util.Optional;
import org.example.entity.UserEntity;

public interface UserEntityRepository extends CustomRepository<Long, UserEntity> {

    Optional<UserEntity> findByUsername(String username);

}
