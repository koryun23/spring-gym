package org.example.dto.response;

import java.util.List;
import java.util.Objects;
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
public class TraineeDeletionResponseDto {

    private boolean status;

    private List<String> errors;

    public TraineeDeletionResponseDto(boolean status) {
        this.status = status;
    }

    public TraineeDeletionResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
