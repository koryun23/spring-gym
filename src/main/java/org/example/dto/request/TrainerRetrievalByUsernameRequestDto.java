package org.example.dto.request;

import lombok.AllArgsConstructor;
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
public class TrainerRetrievalByUsernameRequestDto {

    private String retrieverUsername;
    private String retrieverPassword;

    private String username;

    public TrainerRetrievalByUsernameRequestDto(String username) {
        this.username = username;
    }
}
