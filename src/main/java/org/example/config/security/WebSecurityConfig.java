package org.example.config.security;

import org.example.mdc.MdcFilter;
import org.example.security.JwtConverter;
import org.example.security.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtConverter jwtConverter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final MdcFilter mdcFilter;

    /**
     * Constructor.
     */
    public WebSecurityConfig(AuthenticationManager authenticationManager,
                             JwtAuthenticationFilter jwtAuthenticationFilter,
                             JwtConverter jwtConverter,
                             AccessDeniedHandler accessDeniedHandler,
                             AuthenticationEntryPoint authenticationEntryPoint, MdcFilter mdcFilter) {
        this.authenticationManager = authenticationManager;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtConverter = jwtConverter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.mdcFilter = mdcFilter;
    }

    /**
     * Security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(mdcFilter, JwtAuthenticationFilter.class)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter))
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers("/trainees/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainings/trainee/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainers/*").hasAuthority("TRAINER")
                .requestMatchers("/trainings/trainer/*").hasAuthority("TRAINER")
                .anyRequest().authenticated())
            .authenticationManager(authenticationManager)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .logout(logout -> logout
                .logoutUrl("/users/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID"))
            .exceptionHandling(e ->
                e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint))
            .build();
    }
}
