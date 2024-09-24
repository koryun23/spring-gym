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
public class TrainingUpdateRequestDto {

    private String updaterUsername;
    private String updaterPassword;

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private Long trainingTypeId;
    private Date date;
    private Long duration;
}
