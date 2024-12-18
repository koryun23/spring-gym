package org.example.security.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.validator.JwtValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtValidator jwtValidator;

    public LogoutHandlerImpl(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("Performing a logout.");
        jwtValidator.validateBearerToken(request.getHeader("Authorization"));

    }
}
