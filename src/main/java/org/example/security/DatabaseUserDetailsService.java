package org.example.security;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.user.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DatabaseUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity dbUser = userService.getByUsername(username);
        return new User(
            dbUser.getUsername(), dbUser.getPassword(),
            dbUser.getUserRoleEntityList().stream()
                .map(UserRoleEntity::getRole)
                .map(UserRoleType::toString)
                .map(SimpleGrantedAuthority::new)
                .toList()
        );
    }
}
