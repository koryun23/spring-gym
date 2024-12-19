package org.example.config.security;

import org.example.mdc.MdcFilter;
import org.example.security.auth.UsernamePasswordAuthenticationFilter;
import org.example.security.jwt.JwtConverter;
import org.example.service.core.user.LoginAttemptService;
import org.example.validator.JwtValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] SWAGGER_URI_WHITELIST = {
        "/swagger-ui/**",
        "/api-docs/**"
    };

    private final JwtConverter jwtConverter;
    private final JwtDecoder jwtDecoder;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final MdcFilter mdcFilter;
    private final LogoutSuccessHandler logoutSuccessHandler;
    private final LogoutHandler logoutHandler;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final LoginAttemptService loginAttemptService;
    private final JwtValidator jwtValidator;

    /**
     * Constructor.
     */
    public WebSecurityConfig(JwtConverter jwtConverter,
                             @Qualifier("jwtDecoder") JwtDecoder jwtDecoder,
                             AccessDeniedHandler accessDeniedHandler,
                             AuthenticationEntryPoint authenticationEntryPoint,
                             MdcFilter mdcFilter,
                             LogoutSuccessHandler logoutSuccessHandler,
                             LogoutHandler logoutHandler,
                             AuthenticationConfiguration authenticationConfiguration,
                             AuthenticationSuccessHandler authenticationSuccessHandler,
                             AuthenticationFailureHandler authenticationFailureHandler,
                             LoginAttemptService loginAttemptService, JwtValidator jwtValidator) {
        this.jwtConverter = jwtConverter;
        this.jwtDecoder = jwtDecoder;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.mdcFilter = mdcFilter;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.logoutHandler = logoutHandler;
        this.authenticationConfiguration = authenticationConfiguration;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.loginAttemptService = loginAttemptService;
        this.jwtValidator = jwtValidator;
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
                usernamePasswordAuthenticationFilter(),
                org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(mdcFilter, LogoutFilter.class)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter)
                    .decoder(jwtDecoder)
                )
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                .requestMatchers("/trainees/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainings/trainee/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainers/*").hasAuthority("TRAINER")
                .requestMatchers("/trainings/trainer/*").hasAuthority("TRAINER")
                .requestMatchers(HttpMethod.POST, "/trainings").hasAuthority("SUPER_USER")
                .requestMatchers(SWAGGER_URI_WHITELIST).hasAuthority("SUPER_USER")
                .requestMatchers("/actuator/**").hasAuthority("SUPER_USER")
                .anyRequest().authenticated())
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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
        throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * UsernamePasswordAuthenticationFilter bean.
     */
    @Bean
    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() throws Exception {
        return new UsernamePasswordAuthenticationFilter(
            authenticationManager(authenticationConfiguration),
            loginAttemptService,
            authenticationSuccessHandler,
            authenticationFailureHandler,
            jwtValidator
        );
    }
}
