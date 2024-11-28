package org.example.service.impl.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserRoleType;
import org.example.service.core.jwt.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtServiceImpl implements JwtService {

    private final JwtBuilder jwtBuilder;
    private final JwtParser jwtParser;

    @Value("${jwt.refresh.token.expiration}")
    long refreshTokenExpirationMillis;

    @Value("${jwt.access.token.expiration}")
    long accessTokenExpirationMillis;

    /**
     * Constructor.
     */
    public JwtServiceImpl(JwtBuilder jwtBuilder, JwtParser jwtParser) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
    }

    @Override
    public String getAccessToken(String username, List<UserRoleType> roles) {
        return jwtBuilder
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
            .claim("tokenId", UUID.randomUUID().toString())
            .claim("username", username)
            .claim("roles", roles)
            .compact();
    }

    @Override
    public String refreshAccessToken(String refreshToken) {
        return getAccessToken(this.getUsernameFromJwt(refreshToken), this.getRolesFromJwt(refreshToken));
    }

    @Override
    public String getRefreshToken(String username, List<UserRoleType> roles) {
        return jwtBuilder
            .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMillis))
            .claim("tokenId", UUID.randomUUID().toString())
            .claim("username", username)
            .claim("roles", roles)
            .compact();
    }

    @Override
    public boolean isExpired(String jwt) {
        //return JWT.decode(jwt).getExpiresAt().before(new Date(System.currentTimeMillis()));
        return false;
    }

    @Override
    public String getUsernameFromJwt(String jwt) {
        System.out.println(jwt);
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return (String) body.get("username");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserRoleType> getRolesFromJwt(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return (List<UserRoleType>) body.get("roles");
    }
}
