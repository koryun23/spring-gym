package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity dbUser = userService.getByUsername(username);
        log.info("retreived user from db - {}", dbUser);
        User user = new User(
            dbUser.getUsername(),
            passwordEncoder.encode(dbUser.getPassword()), // this is already encoded
            dbUser.getUserRoleEntityList().stream()
                .map(UserRoleEntity::getRole)
                .map(UserRoleType::toString)
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(
            user, null, user.getAuthorities()
        ));
        SecurityContextHolder.setContext(context);
        return user;
    }
}
