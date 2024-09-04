package org.example.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainingCreationResponseDto {

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;

    private List<String> errors;

    public TrainingCreationResponseDto(Long trainingId, Long traineeId, Long trainerId, String name, TrainingType trainingType, Date trainingDate, Long duration) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }

    public TrainingCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
