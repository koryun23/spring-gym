package org.example.dto.request;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingCreationRequestDto {

    @ToString.Exclude
    private String traineeUsername;

    @ToString.Exclude
    private String trainerUsername;

    private String trainingName;
    private Date trainingDate;
    private Long trainingDuration;

}
