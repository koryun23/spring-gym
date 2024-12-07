package org.example.service.impl.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    private final ObjectMapper objectMapper;

    @Value("${jwt.refresh.token.expiration}")
    private long refreshTokenExpirationMillis;

    @Value("${jwt.access.token.expiration}")
    private long accessTokenExpirationMillis;

    /**
     * Constructor.
     */
    public JwtServiceImpl(JwtBuilder jwtBuilder, JwtParser jwtParser, ObjectMapper objectMapper) {
        this.jwtBuilder = jwtBuilder;
        this.jwtParser = jwtParser;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getAccessToken(String username, List<UserRoleType> roles) {
        return jwtBuilder
            .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationMillis))
            .setIssuedAt(new Date(System.currentTimeMillis()))
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
        DecodedJWT decodedJwt = JWT.decode(jwt);
        return decodedJwt.getExpiresAt().before(new Date(System.currentTimeMillis()));
    }

    @Override
    public String getUsernameFromJwt(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return (String) body.get("username");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UserRoleType> getRolesFromJwt(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return (List<UserRoleType>) body.get("roles");
    }

    @Override
    public String getTokenIdFromJwt(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return (String) body.get("tokenId");
    }

    @Override
    public Date getIssuedAt(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return body.getIssuedAt();
    }

    @Override
    public Date getExpiration(String jwt) {
        Claims body = (Claims) jwtParser.parse(jwt).getBody();
        return body.getExpiration();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getHeadersAsMap(String jwt) throws IOException {
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String[] splitJwt = jwt.split("\\.");
        byte[] decode = decoder.decode(splitJwt[0]);
        Map<String, Object> map = (Map<String, Object>) objectMapper.readValue(decode, Map.class);
        return map;
    }

}
