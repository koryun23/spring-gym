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
public class TrainingListRetrievalByTrainerRequestDto {

    @ToString.Exclude
    private String trainerUsername;

    private String from;
    private String to;

    @ToString.Exclude
    private String traineeUsername;
}
