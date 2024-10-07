package org.example.dto.plain;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingDto {

    private String traineeUsername;
    private String trainerUsername;
    private String trainingName;
    private Date trainingDate;
    private Long trainingDuration;
}
