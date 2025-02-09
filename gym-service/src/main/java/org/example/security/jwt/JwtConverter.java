package org.example.security.jwt;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public JwtAuthenticationToken convert(Jwt source) {
        log.info("Source jwt - {}", source);
        List<String> roles = source.getClaim("roles");
        return new JwtAuthenticationToken(source, roles.stream().map(SimpleGrantedAuthority::new).toList(),
            source.getClaim("username"));
    }
}
