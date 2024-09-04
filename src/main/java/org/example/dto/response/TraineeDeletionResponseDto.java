package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
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
