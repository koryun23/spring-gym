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
        if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
            throw new JwtException("Token is invalid");
        }
        validateJwt(bearerToken.substring(7));
    }

    /**
     * A method for validating the given jwt token.
     */
    public void validateJwt(String jwt) {
        log.info("Validating the given jwt token - {}", jwt);
        if (jwtService.isExpired(jwt)) {
            throw new JwtException("Token is expired");
        }
        log.info("Successfully validated the given jwt token - {}", jwt);

    }
}
