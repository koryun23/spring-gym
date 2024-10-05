package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TraineeDto;
import org.example.dto.plain.TrainingTypeDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerRetrievalResponseDto {

    private String firstName;
    private String lastName;
    private TrainingTypeDto trainingTypeDto;
    private Boolean isActive;
    private List<TraineeDto> trainees;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerRetrievalResponseDto(String firstName,
                                       String lastName,
                                       TrainingTypeDto trainingTypeDto,
                                       Boolean isActive,
                                       List<TraineeDto> trainees) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.trainingTypeDto = trainingTypeDto;
        this.isActive = isActive;
        this.trainees = trainees;
    }

    /**
     * Constructor.
     */
    public TrainerRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
