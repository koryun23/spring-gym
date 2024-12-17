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
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
    private final UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;
    private final JwtConverter jwtConverter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final MdcFilter mdcFilter;
    private final LogoutFilter logoutFilter; // TODO no usage
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
                             LogoutFilter logoutFilter, LogoutSuccessHandler logoutSuccessHandler,
                             LogoutHandler logoutHandler) {
        this.authenticationManager = authenticationManager;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
        this.jwtConverter = jwtConverter;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.mdcFilter = mdcFilter;
        this.logoutFilter = logoutFilter;
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
                usernamePasswordAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(mdcFilter, UsernamePasswordAuthenticationFilter.class)
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwtAuthenticationConverter(jwtConverter))
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/trainees").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainers").permitAll()
                // TODO I don't think you need to add this for login,
                //  it will be added by spring security based on the filter class configuration.
                .requestMatchers(HttpMethod.GET, "/users/login").permitAll()
                // TODO Why do you give everyone access to the logout endpoint?
                .requestMatchers(HttpMethod.GET, "/users/logout").permitAll()
                // TODO I think setting authorities on controllers using annotations is better
                //  and will make filter chain easier to read.
                .requestMatchers("/trainees/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainings/trainee/*").hasAuthority("TRAINEE")
                .requestMatchers("/trainers/*").hasAuthority("TRAINER")
                .requestMatchers("/trainings/trainer/*").hasAuthority("TRAINER")
                .anyRequest().authenticated())
            .authenticationManager(authenticationManager)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .logout(logout -> logout
                // TODO I think this will give access to the logout endpoint using all http methods.
                //  It would be better to use one type of method.
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
