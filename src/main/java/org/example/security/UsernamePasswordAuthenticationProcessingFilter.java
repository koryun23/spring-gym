package org.example.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

@Order(0)
@Log4j2
@Component
public class UsernamePasswordAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;
    private final UsernamePasswordAuthenticationSuccessHandler authSuccessHandler;
    private final UsernamePasswordAuthenticationFailureHandler authFailureHandler;

    /**
     * Constructor.
     */
    public UsernamePasswordAuthenticationProcessingFilter(AuthenticationManager authenticationManager,
                                                          UsernamePasswordAuthenticationSuccessHandler authSuccessHandler,
                                                          UsernamePasswordAuthenticationFailureHandler authFailureHandler) {
        super("http://localhost:8888/login", authenticationManager);
        this.authSuccessHandler = authSuccessHandler;
        this.authFailureHandler = authFailureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {
        String username = request.getHeader("username");
        String password = request.getHeader("password");
        Authentication authentication =
            new UsernamePasswordAuthenticationToken(new User(username, password, Collections.emptyList()), null);
        authentication = authenticationManager.authenticate(authentication);
        if (authentication.isAuthenticated()) {
            authSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        } else {
            authFailureHandler.onAuthenticationFailure(request, response,
                new AuthenticationServiceException("Authentication failed"));
        }
        return authentication;
    }
}
