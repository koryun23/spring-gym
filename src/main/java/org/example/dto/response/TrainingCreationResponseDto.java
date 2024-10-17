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
public class TrainingCreationResponseDto {

    private HttpStatus httpStatus;

    /**
     * Constructor.
     */
    public TrainingCreationResponseDto(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
