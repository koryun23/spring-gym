package org.example.dto.request;

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
public class UserCreationRequestDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;

    /**
     * Constructor.
     */
    public UserCreationRequestDto(String firstName, String lastName, Boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
    }
}
