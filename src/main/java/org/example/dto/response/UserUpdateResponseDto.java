package org.example.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserUpdateResponseDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;

    /**
     * Constructor.
     */
    public UserUpdateResponseDto(Long userId, String firstName, String lastName, String username, String password,
                                 Boolean isActive) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }
}
