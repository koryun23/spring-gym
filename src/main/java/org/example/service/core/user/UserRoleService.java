package org.example.service.core.user;

import java.util.List;
import org.example.entity.user.UserRoleEntity;

public interface UserRoleService {

    UserRoleEntity create(UserRoleEntity userRoleEntity);

    List<UserRoleEntity> findAllByUsername(String username);

    void deleteByUsername(String username);
}
