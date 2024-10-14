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
public class MultipleTrainingDeletionByTraineeResponseDto {

    private String traineeUsername;

    public MultipleTrainingDeletionByTraineeResponseDto(String traineeUsername) {
        this.traineeUsername = traineeUsername;
    }
}
