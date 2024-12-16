package org.example.security;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.example.service.core.jwt.JwtService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component("jwtDecoder")
public class JwtDecoderImpl implements JwtDecoder {

    private final JwtService jwtService;

    public JwtDecoderImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        if (jwtService.isExpired(token)) {
            throw new JwtException("Token is expired");
        }
        return new Jwt(token, issuedAt(token), expiresAt(token), headersMap(token), claimsMap(token));
    }

    private Map<String, Object> claimsMap(String token) {
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("tokenId", jwtService.getTokenIdFromJwt(token));
        claimsMap.put("username", jwtService.getUsernameFromJwt(token));
        claimsMap.put("roles", jwtService.getRolesFromJwt(token));
        System.out.println(claimsMap);
        return claimsMap;
    }

    private Instant issuedAt(String token) {
        return jwtService.getIssuedAt(token).toInstant();
    }

    private Instant expiresAt(String token) {
        return jwtService.getExpiration(token).toInstant();
    }

    private Map<String, Object> headersMap(String token) {
        Map<String, Object> headers = null;
        try {
            headers = jwtService.getHeadersAsMap(token);
        } catch (IOException e) {
            throw new JwtException("Could not extract token headers");
        }
        System.out.println(headers);
        return headers;
    }
}
