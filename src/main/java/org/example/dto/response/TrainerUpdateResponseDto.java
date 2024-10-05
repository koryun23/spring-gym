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
public class TrainerUpdateResponseDto {

    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDto specialization;
    private Boolean isActive;
    private List<TraineeDto> trainees;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerUpdateResponseDto(String username,
                                    String firstName,
                                    String lastName,
                                    TrainingTypeDto specialization,
                                    Boolean isActive,
                                    List<TraineeDto> trainees) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
        this.trainees = trainees;
    }

    /**
     * Constructor.
     */
    public TrainerUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
