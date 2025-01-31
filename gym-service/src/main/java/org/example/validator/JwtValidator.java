package org.example.validator;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.JwtException;
import org.example.service.core.jwt.JwtService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtValidator {

    private final JwtService jwtService;

    public JwtValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    /**
     * A method for validating the given bearer token.
     */
    public void validateBearerToken(String bearerToken) {
        if (!isValidBearerToken(bearerToken)) {
            throw new JwtException("Bearer token is invalid.");
        }
    }

    /**
     * A method for validating the given jwt token.
     */
    public void validateJwt(String jwt) {
        if (!isValidJwtToken(jwt)) {
            throw new JwtException("Jwt token is invalid");
        }
    }

    public boolean isValidJwtToken(String jwt) {
        return jwt != null && !jwtService.isExpired(jwt);
    }

    public boolean isValidBearerToken(String bearer) {
        return bearer != null && bearer.startsWith("Bearer ") && isValidJwtToken(bearer.substring(7));
    }
}
