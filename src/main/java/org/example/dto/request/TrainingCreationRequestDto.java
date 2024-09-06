package org.example.dto.request;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainingCreationRequestDto {

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;

    public TrainingCreationRequestDto(Long traineeId, Long trainerId, String name,
                                      TrainingType trainingType, Date trainingDate, Long duration) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }
}
