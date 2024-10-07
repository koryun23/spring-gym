package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeSwitchActivationStateResponseDto {

    private HttpStatus httpStatus;

    private List<String> errors;

    public TraineeSwitchActivationStateResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public TraineeSwitchActivationStateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
