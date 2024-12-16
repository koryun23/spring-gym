package org.example.config.security;

import org.example.security.DatabaseUserDetailsService;
import org.example.security.JwtConverter;
import org.example.security.JwtDecoderImpl;
import org.example.service.core.user.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return new DatabaseUserDetailsService(userService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Authentication provider bean.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
                                                         PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /**
     * Jwt Authentication provider bean.
     */
    @Bean
    public AuthenticationProvider jwtAuthenticationProvider(@Qualifier("jwtDecoder") JwtDecoder jwtDecoderImpl, JwtConverter jwtConverter) {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(jwtDecoderImpl);
        jwtAuthenticationProvider.setJwtAuthenticationConverter(jwtConverter);
        return jwtAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
        @Qualifier("authenticationProvider") AuthenticationProvider authenticationProvider,
        @Qualifier("jwtAuthenticationProvider") AuthenticationProvider jwtAuthenticationProvider) {
        return new ProviderManager(authenticationProvider, jwtAuthenticationProvider);
    }
}
