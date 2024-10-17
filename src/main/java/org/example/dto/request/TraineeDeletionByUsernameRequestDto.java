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
public class TraineeDeletionByUsernameRequestDto {

    private String deleterUsername;
    private String deleterPassword;

    private String username;

    public TraineeDeletionByUsernameRequestDto(String username) {
        this.username = username;
    }
}
