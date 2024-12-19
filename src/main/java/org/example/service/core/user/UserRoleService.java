package org.example.service.core.user;

import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;

public interface UserRoleService {

    UserRoleEntity create(UserRoleEntity userRoleEntity);

    Integer countByUserRoleType(UserRoleType userRoleType);
}
