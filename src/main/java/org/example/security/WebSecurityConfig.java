package org.example.security;

import org.example.filter.FilterExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FilterExceptionHandler filterExceptionHandler;

    public WebSecurityConfig(AuthenticationManager authenticationManager, AuthenticationProvider authenticationProvider,
                             JwtAuthenticationFilter jwtAuthenticationFilter,
                             FilterExceptionHandler filterExceptionHandler) {
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.filterExceptionHandler = filterExceptionHandler;
    }

    /**
     * Security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/trainees/*").hasAuthority("TRAINEE")
                .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider)
            .authenticationManager(authenticationManager)
            .build();
    }
}
