package org.example.dto.plain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class UserDto {

    private String firstName;
    private String lastName;

    @ToString.Exclude
    private String username;

    @ToString.Exclude
    private String password;

    private Boolean isActive;
}
