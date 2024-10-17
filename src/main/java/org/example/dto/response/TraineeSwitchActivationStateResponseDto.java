package org.example.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeSwitchActivationStateResponseDto {

    private HttpStatus httpStatus;

    public TraineeSwitchActivationStateResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
