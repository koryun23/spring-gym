package org.example.dto.response;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingRetrievalResponseDto {

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private Long trainingTypeId;
    private Date trainingDate;
    private Long duration;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainingRetrievalResponseDto(Long trainingId, Long traineeId, Long trainerId, String name,
                                        Long trainingTypeId,
                                        Date trainingDate, Long duration) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingTypeId = trainingTypeId;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }

    public TrainingRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
