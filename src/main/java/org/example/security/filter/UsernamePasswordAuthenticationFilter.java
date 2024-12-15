package org.example.security.filter;

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
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    /**
     * Constructor.
     */
    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                UserService userService,
                                                JwtService jwtService) {
        super(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users/login"));
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        AuthHolder.setId(request.getRemoteAddr());

        if (LocalDateTime.now().isBefore(AuthHolder.getBlockedUntil())) {
            response.setStatus(401);
            log.info("Cannot login until {}", AuthHolder.getBlockedUntil());
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
        log.info("Auth Holder before - {}", AuthHolder.print());

        AuthHolder.setAttemptCounter(AuthHolder.getAttemptCounter() + 1);

        if (AuthHolder.getAttemptCounter() == 3) {
            AuthHolder.setBlockedUntil(
                LocalDateTime.now().plusMinutes(5)); // TODO: Move the minutes to application.properties
            AuthHolder.setAttemptCounter(0);
        }
        log.info("Auth Holder after - {}", AuthHolder.print());

        response.setStatus(401);
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
