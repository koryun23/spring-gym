package org.example.security;

import org.example.service.core.jwt.JwtService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class JwtDecoderImpl implements JwtDecoder {

    private final JwtService jwtService;
    private final NimbusJwtDecoder nimbusJwtDecoder;

    public JwtDecoderImpl(JwtService jwtService, NimbusJwtDecoder nimbusJwtDecoder) {
        this.jwtService = jwtService;
        this.nimbusJwtDecoder = nimbusJwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        if (jwtService.isExpired(token)) {
            throw new JwtException("Token is expired");
        }
        return nimbusJwtDecoder.decode(token);

    }
}
