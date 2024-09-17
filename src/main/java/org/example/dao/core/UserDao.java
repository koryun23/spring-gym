package org.example.dao.core;

import java.util.Optional;
import org.example.entity.UserEntity;

public interface UserDao extends Dao<UserEntity> {

    UserEntity getByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
