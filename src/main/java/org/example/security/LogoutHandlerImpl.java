package org.example.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.exception.JwtException;
import org.example.service.core.jwt.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtService jwtService;

    public LogoutHandlerImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new JwtException("Token is missing");
        }

        token = token.substring(7);
        if (jwtService.isExpired(token)) {
            throw new JwtException("Token is expired");
        }

        // TODO I think you need to invalidate the given jwt token when logging out.

    }
}
