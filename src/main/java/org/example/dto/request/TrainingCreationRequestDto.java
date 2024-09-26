package org.example.dto.request;

import java.sql.Date;
import lombok.AllArgsConstructor;
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

    private Long traineeId;
    private Long trainerId;
    private String name;
    private Long trainingTypeId;
    private Date trainingDate;
    private Long duration;

    /**
     * Constructor.
     */
    public TrainingCreationRequestDto(String creatorUsername, String creatorPassword, Long traineeId, Long trainerId,
                                      String name, Long trainingTypeId, Date trainingDate, Long duration) {
        this.creatorUsername = creatorUsername;
        this.creatorPassword = creatorPassword;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingTypeId = trainingTypeId;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }
}
