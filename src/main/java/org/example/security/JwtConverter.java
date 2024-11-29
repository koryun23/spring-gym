package org.example.security;

import java.util.List;
import org.example.entity.user.UserRoleType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public AbstractAuthenticationToken convert(Jwt source) {
        List<UserRoleType> roles = (List<UserRoleType>) source.getClaim("roles");
        return new JwtAuthenticationToken(source,
            roles.stream().map(UserRoleType::toString).map(SimpleGrantedAuthority::new).toList());
    }
}
