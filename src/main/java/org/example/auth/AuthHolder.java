package org.example.auth;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthHolder {

    @Getter
    @Setter
    private static LocalDateTime blockedUntil = LocalDateTime.MIN;

    @Getter
    @Setter
    private static String sessionId;

    @Getter
    @Setter
    private static int attemptCounter = 0;

    public static void reset() {
        blockedUntil = LocalDateTime.MIN;
        attemptCounter = 0;
    }

    public static String print() {
        return String.format("AuthHolder: Blocked Until - %s; Session Id - %s; Attempt Counter - %s", blockedUntil,
            sessionId, attemptCounter);
    }
}
