package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.AuthenticationFailureException;
import org.example.service.core.AuthenticatorService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@WebFilter(urlPatterns = {"/trainees/*", "/trainers/*", "/users/*", "/trainings/*", "/training-types/*"})
public class AuthFilter extends OncePerRequestFilter {

    private AuthenticatorService authenticatorService;

    public AuthFilter(AuthenticatorService authenticatorService) {
        this.authenticatorService = authenticatorService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        log.info("Attempting authentication");

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        if (username == null || username.isEmpty()) {
            throw new AuthenticationFailureException("Username is required");
        }

        if (password == null || password.isEmpty()) {
            throw new AuthenticationFailureException("Password is required");
        }

        if (authenticatorService.authFail(username, password)) {
            throw new AuthenticationFailureException("Authentication failed");
        }

        filterChain.doFilter(request, response);
    }
}
