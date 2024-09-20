package org.example.dto.request;

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
public class TrainingListRetrievalByTraineeTrainerRequestDto {

    private String retrieverUsername;
    private String retrieverPassword;

    private String traineeUsername;
    private String trainerUsername;

}
