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

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final FilterExceptionHandler filterExceptionHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;

    public WebSecurityConfig(AuthenticationManager authenticationManager, AuthenticationProvider authenticationProvider,
                             JwtAuthenticationFilter jwtAuthenticationFilter,
                             FilterExceptionHandler filterExceptionHandler,
                             JwtAuthorizationFilter jwtAuthorizationFilter) {
        this.authenticationManager = authenticationManager;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.filterExceptionHandler = filterExceptionHandler;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
    }

    /**
     * Security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, JwtAuthenticationFilter.class)
            .addFilterBefore(filterExceptionHandler, JwtAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                .requestMatchers("/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/trainees/*").hasAuthority("TRAINER")
                //.requestMatchers("/logout").permitAll()
                .anyRequest().authenticated())
            .authenticationProvider(authenticationProvider)
            .authenticationManager(authenticationManager)
            .logout(logout -> logout.logoutUrl("https://localhost:8888/logout"))
            .build();
    }
}
