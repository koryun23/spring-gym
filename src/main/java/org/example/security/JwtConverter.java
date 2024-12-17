package org.example.security;

import java.util.List;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public JwtAuthenticationToken convert(Jwt source) {

        List<String> roles = source.getClaim("roles");
        return new JwtAuthenticationToken(source, roles.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
