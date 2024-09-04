package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

import java.util.Date;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainingCreationRequestDto {
    private Long traineeId;
    private Long trainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;
}
