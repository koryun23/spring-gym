package org.example.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import org.example.dto.RestResponse;
import org.example.exception.DoubleLoginException;
import org.example.exception.LoginBlockedException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    public AuthenticationFailureHandlerImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(401);

        RestResponse restResponse = null;
        if (exception instanceof DoubleLoginException) {
            restResponse =
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(), List.of(exception.getMessage()));
        } else if (exception instanceof LoginBlockedException) {
            restResponse =
                new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(), List.of(exception.getMessage()));
        } else {
            restResponse = new RestResponse(null, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
                List.of("Authentication failed: invalid credentials"));
        }
        objectMapper.writeValue(response.getWriter(), restResponse);
    }
}
