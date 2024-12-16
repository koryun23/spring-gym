package org.example.auth;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.LoginRequestDto;
import org.springframework.stereotype.Component;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthHolder {

    private static AuthHolder authHolder;

    private LoginRequestDto loginRequestDto;

    private AuthHolder(LoginRequestDto loginRequestDto) {
        this.loginRequestDto = loginRequestDto;
    }

    /**
     * Abstract factory method for obtaining an empty authentication holder.
     */
    public static AuthHolder ofEmpty(String id) {
        if (authHolder == null) {
            authHolder = new AuthHolder(new LoginRequestDto(id, 0, LocalDateTime.now()));
        }
        return authHolder;
    }

    public static AuthHolder of(LoginRequestDto loginRequestDto) {
        authHolder = new AuthHolder(loginRequestDto);
        return authHolder;
    }

    /**
     * Method for incrementing the login attempt counter.
     */
    public void attemptLogin() {
        if (authHolder == null) {
            authHolder = AuthHolder.ofEmpty("");
        }
        loginRequestDto.setCounter(loginRequestDto.getCounter() + 1);
        authHolder.setLoginRequestDto(loginRequestDto);
    }

}
