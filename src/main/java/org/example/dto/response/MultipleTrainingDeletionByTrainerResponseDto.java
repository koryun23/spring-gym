package org.example.dto.response;

import java.util.List;
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
public class MultipleTrainingDeletionByTrainerResponseDto {

    private String trainerUsername;

    private List<String> errors;

    public MultipleTrainingDeletionByTrainerResponseDto(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }

    public MultipleTrainingDeletionByTrainerResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
