package org.example.service.core.jwt;

import java.util.Date;
import java.util.List;
import org.example.entity.user.UserRoleType;

public interface JwtService {

    String getAccessToken(String username, List<UserRoleType> roles);

    String refreshAccessToken(String refreshToken);

    String getRefreshToken(String username, List<UserRoleType> roles);

    boolean isExpired(String jwt);

    String getUsernameFromJwt(String jwt);

    List<UserRoleType> getRolesFromJwt(String jwt);

    String getTokenIdFromJwt(String jwt);

    Date getIssuedAt(String jwt);

    Date getExpiration(String jwt);
}
