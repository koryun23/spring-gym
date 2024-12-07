package org.example.security;

import java.util.List;
import org.example.entity.user.UserEntity;
import org.example.service.core.user.UserService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JwtConverter implements Converter<Jwt, JwtAuthenticationToken> {

    private final UserService userService;

    public JwtConverter(UserService userService) {
        this.userService = userService;
    }

    @SuppressWarnings("unchecked")
    @Override
    @NonNull
    public JwtAuthenticationToken convert(Jwt source) {

        String username = (String) source.getClaim("username");
        UserEntity user = userService.getByUsername(username);

        List<String> roles = source.getClaim("roles");
        System.out.println(roles);
        return new JwtAuthenticationToken(source,
            roles.stream().map(SimpleGrantedAuthority::new).toList());
    }
}
