package org.example.dto.request;

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
public class TrainingListRetrievalByTraineeRequestDto {

    private String traineeUsername;
    private Date from;
    private Date to;
    private String trainerUsername;
    private Long specializationId;
}
