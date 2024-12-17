package org.example.config.security;

import org.example.mdc.MdcFilter;
import org.example.security.JwtConverter;
import org.example.security.filter.UsernamePasswordAuthenticationFilter;
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
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        "/v3/api-docs/**",
        "/api/public/**",
        "/api/public/authenticate",
        "/actuator/*",
        "/swagger-ui/**",
        "/api-docs"
    };
    private final AuthenticationManager authenticationManager;
    private final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private final JwtConverter jwtConverter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final MdcFilter mdcFilter;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final LogoutHandler logoutHandler;

    /**
     * Constructor.
     */
    public WebSecurityConfig(AuthenticationManager authenticationManager,
                             UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
                             JwtConverter jwtConverter,
                             AccessDeniedHandler accessDeniedHandler,
                             AuthenticationEntryPoint authenticationEntryPoint, MdcFilter mdcFilter,
                             LogoutSuccessHandler logoutSuccessHandler,
                             LogoutHandler logoutHandler) {
        this.authenticationManager = authenticationManager;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
        this.jwtConverter = jwtConverter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.mdcFilter = mdcFilter;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.logoutHandler = logoutHandler;
    }

    /**
     * Security filter chain.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(
                usernamePasswordAuthenticationFilter,
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(mdcFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter))
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/logout").permitAll()
                .requestMatchers(AUTH_WHITELIST).permitAll()
                .requestMatchers("/trainees/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainings/trainee/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainers/*").hasAuthority("TRAINER")
                .requestMatchers("/trainings/trainer/*").hasAuthority("TRAINER")
                .anyRequest().authenticated())
            .authenticationManager(authenticationManager)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .logout(logout -> logout
                .logoutUrl("/users/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .addLogoutHandler(logoutHandler)
                .clearAuthentication(true)
                .invalidateHttpSession(true))
            .exceptionHandling(e ->
                e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint))
            .build();
    }
}
