package org.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.AuthHolder;
import org.example.auth.UnsuccessfulAuthRequest;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.jwt.JwtService;
import org.example.service.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthHolder authHolder;

    /**
     * Constructor.
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   UserService userService,
                                   JwtService jwtService,
                                   AuthHolder authHolder) {
        super(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users/login"));
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.authHolder = authHolder;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        UnsuccessfulAuthRequest unsuccessfulAuthRequest = authHolder.getUnsuccessfulAuthRequest();
        if (unsuccessfulAuthRequest != null && unsuccessfulAuthRequest.getBlockedUntil() != null
            && LocalDateTime.now().isBefore(unsuccessfulAuthRequest.getBlockedUntil())) {
            response.setStatus(401);
            return null;
        }

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            new User(username, password, Collections.emptyList()), password
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        UserEntity user = userService.getByUsername(authResult.getName());
        String username = user.getUsername();
        String password = user.getPassword();
        List<UserRoleType> userRoles = user.getUserRoleEntityList().stream().map(
            UserRoleEntity::getRole).toList();

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
            user, password, userRoles.stream().map(UserRoleType::toString).map(SimpleGrantedAuthority::new).toList()
        ));

        response.setHeader("Access Token", jwtService.getAccessToken(username, userRoles));
        response.setHeader("Refresh Token", jwtService.getRefreshToken(username, userRoles));
        response.setStatus(200);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("Auth Holder before - {}", authHolder);
        String sessionId = request.getSession().getId();
        UnsuccessfulAuthRequest unsuccessfulAuthRequest = authHolder.getUnsuccessfulAuthRequest();
        if (unsuccessfulAuthRequest == null) {
            authHolder.setUnsuccessfulAuthRequest(new UnsuccessfulAuthRequest(
                sessionId,
                null,
                1
            ));
        } else {
            unsuccessfulAuthRequest.setAttemptCounter(unsuccessfulAuthRequest.getAttemptCounter() + 1);
            if (unsuccessfulAuthRequest.getAttemptCounter() == 3) {
                unsuccessfulAuthRequest.setBlockedUntil(LocalDateTime.now().plusMinutes(5));
            }
            if (LocalDateTime.now().isAfter(unsuccessfulAuthRequest.getBlockedUntil())) {
                unsuccessfulAuthRequest.setBlockedUntil(null);
                unsuccessfulAuthRequest.setAttemptCounter(0);
            }
            authHolder.setUnsuccessfulAuthRequest(unsuccessfulAuthRequest);
        }
        log.info("Auth Holder after - {}", authHolder);

        response.setStatus(401);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
