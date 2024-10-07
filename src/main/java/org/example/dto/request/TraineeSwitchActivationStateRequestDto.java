package org.example.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TraineeSwitchActivationStateRequestDto {

    private String updaterUsername;
    private String updaterPassword;

    private String username;

    public TraineeSwitchActivationStateRequestDto(String username) {
        this.username = username;
    }
}
