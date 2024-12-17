package org.example.auth;

import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.LoginAttemptDto;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthHolder {

    private static AuthHolder authHolder;

    private LoginAttemptDto loginAttemptDto;

    private AuthHolder(LoginAttemptDto loginAttemptDto) {
        this.loginAttemptDto = loginAttemptDto;
    }

    /**
     * Abstract factory method for obtaining an empty authentication holder.
     */
    public static AuthHolder ofEmpty(String id) {
        if (authHolder == null) {
            authHolder = new AuthHolder(new LoginAttemptDto(id, 0, LocalDateTime.now()));
        }
        return authHolder;
    }

    public static AuthHolder of(LoginAttemptDto loginAttemptDto) {
        authHolder = new AuthHolder(loginAttemptDto);
        return authHolder;
    }

    /**
     * Method for incrementing the login attempt counter.
     */
    public void attemptLogin() {
        if (authHolder == null) {
            authHolder = AuthHolder.ofEmpty("");
        }
        loginAttemptDto.setCounter(loginAttemptDto.getCounter() + 1);
        authHolder.setLoginAttemptDto(loginAttemptDto);
    }

}
