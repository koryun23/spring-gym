package org.example.security.authorization;

import org.example.validator.JwtValidator;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class JwtDecoderImpl implements JwtDecoder {

    private final JwtValidator jwtValidator;
    private final NimbusJwtDecoder nimbusJwtDecoder;

    public JwtDecoderImpl(JwtValidator jwtValidator, NimbusJwtDecoder nimbusJwtDecoder) {
        this.jwtValidator = jwtValidator;
        this.nimbusJwtDecoder = nimbusJwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        jwtValidator.validateJwt(token);
        return nimbusJwtDecoder.decode(token);
    }
}
