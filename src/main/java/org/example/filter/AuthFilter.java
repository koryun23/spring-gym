package org.example.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.AuthenticationFailureException;
import org.example.service.core.user.AuthenticatorService;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    private final AuthenticatorService authenticatorService;

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

        log.info("Successfully authenticated");

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return (request.getRequestURI().startsWith("/trainees") && "POST".equals(request.getMethod()))
            || (request.getRequestURI().startsWith("/trainers") && "POST".equals(request.getMethod()));
    }
}
