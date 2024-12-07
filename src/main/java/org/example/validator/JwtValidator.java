package org.example.validator;

import org.example.dto.RestResponse;
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
     * A method for validating the given jwt token.
     */
    public RestResponse validateJwt(String jwt) {
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new JwtException("Token is invalid");
        }
        jwt = jwt.substring(7);
        if (jwtService.isExpired(jwt)) {
            throw new JwtException("Token is expired");
        }
        return null;
    }
}
