package org.example.dto.response;

import java.sql.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingUpdateResponseDto {

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private Long trainingTypeId;
    private Date date;
    private Long duration;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainingUpdateResponseDto(Long trainingId,
                                     Long traineeId,
                                     Long trainerId,
                                     String name,
                                     Long trainingTypeId,
                                     Date date,
                                     Long duration) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingTypeId = trainingTypeId;
        this.date = date;
        this.duration = duration;
    }

    public TrainingUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
