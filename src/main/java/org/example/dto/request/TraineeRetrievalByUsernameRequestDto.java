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
public class TraineeRetrievalByUsernameRequestDto {

    private String retrieverUsername;
    private String retrieverPassword;

    private String username;

    public TraineeRetrievalByUsernameRequestDto(String username) {
        this.username = username;
    }
}
