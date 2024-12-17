package org.example.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.AuthHolder;
import org.example.dto.plain.LoginRequestDto;
import org.example.entity.user.LoginAttemptEntity;
import org.example.entity.user.UserEntity;
import org.example.entity.user.UserRoleEntity;
import org.example.entity.user.UserRoleType;
import org.example.service.core.jwt.JwtService;
import org.example.service.core.user.LoginAttemptService;
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
// TODO I think it would be better to name custom class with a different name from spring's built-in classes.
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;
    private final LoginAttemptService loginAttemptService;

    private AuthHolder authHolder;

    /**
     * Constructor.
     */
    public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager,
                                                UserService userService,
                                                JwtService jwtService, LoginAttemptService loginAttemptService) {
        super(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/users/login"));
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException {

        Optional<LoginAttemptEntity> optionalLoginAttempt = loginAttemptService.findByRemoteAddress(request.getRemoteAddr());
        // TODO I think this logic could be in a separate method.
        //  It would make the attemptAuthentication method easier to read.
        if (optionalLoginAttempt.isPresent()) {
            LoginAttemptEntity loginAttemptEntity = optionalLoginAttempt.get();
            authHolder = AuthHolder.of(new LoginRequestDto(
                loginAttemptEntity.getRemoteAddress(),
                loginAttemptEntity.getCounter(),
                loginAttemptEntity.getBlockedUntil()
            ));
        } else {
            authHolder = AuthHolder.ofEmpty(request.getRemoteAddr());
            loginAttemptService.create(new LoginAttemptEntity(
                request.getRemoteAddr(),
                0,
                LocalDateTime.now()
            ));
        }

        authHolder.getLoginRequestDto().setId(request.getRemoteAddr());

        if (LocalDateTime.now().isBefore(authHolder.getLoginRequestDto().getBlockedUntil())) {
            response.setStatus(401);
            // TODO I dont think it is a good idea to send a concrete message about block time,
            //  I think it's better to give some general message like "Try again later."
            log.info("Cannot login until {}", authHolder.getLoginRequestDto().getBlockedUntil());
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
        // TODO I think you can pass the authResult to the setAuthentication method.
        //  It should already contain the authenticated user.
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

        authHolder.attemptLogin();
        loginAttemptService.incrementCounter(authHolder.getLoginRequestDto().getId());

        // TODO I think it would be better if the logic for failed login db update would be in another method.
        //  It is not the main purpose of the unsuccessfulAuthentication method.
        if (authHolder.getLoginRequestDto().getCounter() == 3) {
            authHolder.getLoginRequestDto().setBlockedUntil(
                LocalDateTime.now().plusMinutes(5)); // TODO: Move the minutes to application.properties
            authHolder.getLoginRequestDto().setCounter(0);

            // update in the database
            loginAttemptService.update(new LoginAttemptEntity(
                authHolder.getLoginRequestDto().getId(),
                authHolder.getLoginRequestDto().getCounter(),
                authHolder.getLoginRequestDto().getBlockedUntil()
            ));
        }

        if (loginAttemptService.findByRemoteAddress(request.getRemoteAddr()).isEmpty()) {
            loginAttemptService.create(new LoginAttemptEntity(
                authHolder.getLoginRequestDto().getId(),
                authHolder.getLoginRequestDto().getCounter(),
                authHolder.getLoginRequestDto().getBlockedUntil()
            ));
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
