package org.example.service.core.jwt;

import java.util.List;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;

public interface JwtService {

    String createJwt(String username, List<UserRoleType> roles);

    String refreshJwt(String username, List<UserRoleType> roles);

    boolean isExpired(String jwt);

    String getUsernameFromJwt(String jwt);

    List<String> getRolesFromJwt(String jwt);
}
