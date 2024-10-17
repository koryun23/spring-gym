package org.example.dto.response;

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

    public MultipleTrainingDeletionByTrainerResponseDto(String trainerUsername) {
        this.trainerUsername = trainerUsername;
    }
}
