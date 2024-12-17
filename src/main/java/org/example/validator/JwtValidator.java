package org.example.validator;

import org.example.exception.JwtException;
import org.example.service.core.jwt.JwtService;
import org.springframework.stereotype.Component;

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
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new JwtException("Token is invalid");
        }
        validateJwt(bearerToken.substring(7));
    }

    /**
     * A method for validating the given jwt token.
     */
    public void validateJwt(String jwt) {
        if (jwtService.isExpired(jwt)) {
            throw new JwtException("Token is expired");
        }

    }
}
