package org.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.example.dto.RestResponse;
import org.example.exception.JwtException;
import org.example.service.core.jwt.JwtService;
import org.example.validator.JwtValidator;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtValidator jwtValidator;
    private final JwtService jwtService;

    public JwtAuthorizationFilter(JwtValidator jwtValidator, JwtService jwtService) {
        this.jwtValidator = jwtValidator;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (invalidToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);
        if (jwtService.isExpired(token)) {
            response.setStatus(401);
            return;
        }
        String username = jwtService.getUsernameFromJwt(token);
        List<SimpleGrantedAuthority> simpleGrantedAuthoritySet = jwtService.getRolesFromJwt(token)
            .stream()
            .map(SimpleGrantedAuthority::new)
            .toList();
        if (username == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        filterChain.doFilter(request, response);
    }

    private String removeJwtPrefix(String jwt) {
        return jwt.substring(7);
    }

    private boolean invalidToken(String token) {
        return token == null || !token.startsWith("Bearer ");
    }
}
