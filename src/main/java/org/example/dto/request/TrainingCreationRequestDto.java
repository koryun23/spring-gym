package org.example.dto.request;

import java.sql.Date;
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
public class TrainingCreationRequestDto {

    private String creatorUsername;
    private String creatorPassword;

    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private Date trainingDate;
    private Long trainingDuration;

    /**
     * Constructor.
     */
    public TrainingCreationRequestDto(String traineeUsername, String trainerUsername,
                                      String trainingName, Date trainingDate, Long trainingDuration) {
        this.traineeUsername = traineeUsername;
        this.trainerUsername = trainerUsername;
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingDuration = trainingDuration;
    }
}
