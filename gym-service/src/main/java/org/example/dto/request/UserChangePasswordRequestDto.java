package org.example.dto.request;

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
public class UserChangePasswordRequestDto {

    @ToString.Exclude
    private String username;

    @ToString.Exclude
    private String oldPassword;

    @ToString.Exclude
    private String newPassword;
}
