package org.example.security.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.auth.LoginAttemptEntity;
import org.example.exception.DoubleLoginException;
import org.example.exception.LoginBlockedException;
import org.example.service.core.user.LoginAttemptService;
import org.example.validator.JwtValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Slf4j
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final LoginAttemptService loginAttemptService;

    private final JwtValidator jwtValidator;

    @Value("${security.login.block.duration.minutes}")
    private int blockMinutes;

    @Value("${security.login.block.attempts}")
    private int attemptsBeforeBlock;

    /**
     * Constructor.
     */
    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                LoginAttemptService loginAttemptService,
                                                AuthenticationSuccessHandler authenticationSuccessHandler,
                                                AuthenticationFailureHandler authenticationFailureHandler,
                                                JwtValidator jwtValidator) {
        super(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users/login"), authenticationManager);
        this.loginAttemptService = loginAttemptService;
        this.jwtValidator = jwtValidator;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        log.info("Attempting authentication");

        String bearerToken = request.getHeader("Authorization");

        if (jwtValidator.isValidBearerToken(bearerToken)) {
            throw new DoubleLoginException("Cannot perform a login because the user is already logged in.");
        }

        Optional<LoginAttemptEntity> optionalLoginAttempt =
            loginAttemptService.findByRemoteAddress(request.getRemoteAddr());

        LoginAttemptEntity loginAttemptEntity = optionalLoginAttempt.orElseGet(
            () -> loginAttemptService.create(new LoginAttemptEntity(request.getRemoteAddr(), 0, LocalDateTime.now())));

        if (LocalDateTime.now().isBefore(loginAttemptEntity.getBlockedUntil())) {
            throw new LoginBlockedException("Too many unsuccessful login attempts: blocked by 5 minutes");
        }

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        return super.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
            new User(username, password, Collections.emptyList()), password
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        loginAttemptService.reset(request.getRemoteAddr());
        super.successfulAuthentication(request, response, chain, authResult);
        log.info("Successfully authenticated");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);

        log.error("Authentication failed");
        Integer counter = loginAttemptService.incrementCounter(request.getRemoteAddr());

        if (counter == attemptsBeforeBlock) {
            loginAttemptService.update(
                new LoginAttemptEntity(request.getRemoteAddr(), 0, LocalDateTime.now().plusMinutes(blockMinutes)));
        }
    }
}
