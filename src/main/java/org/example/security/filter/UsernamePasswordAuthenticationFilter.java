package org.example.security.filter;

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
import org.example.service.core.user.LoginAttemptService;
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
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final LoginAttemptService loginAttemptService;

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
                                                AuthenticationFailureHandler authenticationFailureHandler) {
        super(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users/login"), authenticationManager);
        this.loginAttemptService = loginAttemptService;
        setAuthenticationSuccessHandler(authenticationSuccessHandler);
        setAuthenticationFailureHandler(authenticationFailureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        Optional<LoginAttemptEntity> optionalLoginAttempt =
            loginAttemptService.findByRemoteAddress(request.getRemoteAddr());

        LoginAttemptEntity loginAttemptEntity = optionalLoginAttempt.orElseGet(
            () -> loginAttemptService.create(new LoginAttemptEntity(request.getRemoteAddr(), 0, LocalDateTime.now())));

        if (LocalDateTime.now().isBefore(loginAttemptEntity.getBlockedUntil())) {
            response.setStatus(401);
            log.info("Cannot login until {}", loginAttemptEntity.getBlockedUntil());
            return null;
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

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        super.unsuccessfulAuthentication(request, response, failed);

        Integer counter = loginAttemptService.incrementCounter(request.getRemoteAddr());

        if (counter == attemptsBeforeBlock) {
            loginAttemptService.update(
                new LoginAttemptEntity(request.getRemoteAddr(), 0, LocalDateTime.now().plusMinutes(blockMinutes)));
        }
    }
}
