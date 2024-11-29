package org.example.security;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.example.service.core.jwt.JwtService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

//@Component
public class JwtDecoderImpl implements JwtDecoder {

    private final JwtService jwtService;

    public JwtDecoderImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("tokenId", jwtService.getTokenIdFromJwt(token));
        claimsMap.put("username", jwtService.getUsernameFromJwt(token));
        claimsMap.put("roles", jwtService.getRolesFromJwt(token));

        Instant issuedAt = jwtService.getIssuedAt(token).toInstant();
        Instant expiresAt = jwtService.getExpiration(token).toInstant();

        return new Jwt(
            token, issuedAt, expiresAt, null, claimsMap
        );
    }
}
