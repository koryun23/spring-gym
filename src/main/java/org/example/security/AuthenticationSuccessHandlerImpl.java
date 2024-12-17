package org.example.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.jwt.JwtService;
import org.example.service.core.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final UserService userService;

    public AuthenticationSuccessHandlerImpl(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String username = request.getHeader("username");
        List<UserRoleType> userRoles =
            userService.getByUsername(username).getUserRoleEntityList().stream().map(UserRoleEntity::getRole).toList();
        response.setHeader("Access Token", jwtService.getAccessToken(username, userRoles));
        response.setHeader("Refresh Token", jwtService.getRefreshToken(username, userRoles));
        response.setStatus(200);
        response.getWriter().flush();
    }
}
