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
import org.example.dto.RestResponse;
import org.example.entity.user.UserEntity;
import org.example.service.core.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService,
                                   ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException {

        String username = request.getHeader("username");
        String password = request.getHeader("password");

        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            new User(username, password, Collections.emptyList()), password
        ));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        SecurityContextHolder.getContext().setAuthentication(authResult);
        UserEntity user = userService.getByUsername(authResult.getName());

        SuccessfulAuthenticationResponseDto authSuccessDto =
            new SuccessfulAuthenticationResponseDto(user.getUsername(), null);

        RestResponse restRespnose =
            new RestResponse(authSuccessDto, HttpStatus.OK, LocalDateTime.now(), Collections.emptyList());

        response.setHeader("token", authSuccessDto.getJwt());
        response.setStatus(200);
        response.getWriter().write(objectMapper.writeValueAsString(restRespnose));
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        UnsuccessfulAuthenticationResponseDto authFailDto =
            new UnsuccessfulAuthenticationResponseDto("Authentication failed");
        RestResponse restResponse = new RestResponse(authFailDto, HttpStatus.UNAUTHORIZED, LocalDateTime.now(),
            List.of(authFailDto.getMessage()));
        response.setStatus(401);
        response.getWriter().write(objectMapper.writeValueAsString(restResponse));
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }
}
