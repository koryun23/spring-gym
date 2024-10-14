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

    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDto trainingTypeDto;
    private Boolean isActive;
    private List<TraineeDto> trainees;

    /**
     * Constructor.
     */
    public TrainerRetrievalResponseDto(String username,
                                       String firstName,
                                       String lastName,
                                       TrainingTypeDto trainingTypeDto,
                                       Boolean isActive,
                                       List<TraineeDto> trainees) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.trainingTypeDto = trainingTypeDto;
        this.isActive = isActive;
        this.trainees = trainees;
    }
}
