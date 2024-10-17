package org.example.dto.request;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainerDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeTrainerListUpdateRequestDto {

    private String updaterUsername;
    private String updaterPassword;

    private String traineeUsername;
    private List<TrainerDto> trainerDtoList;

    public TraineeTrainerListUpdateRequestDto(String traineeUsername, List<TrainerDto> trainerDtoList) {
        this.traineeUsername = traineeUsername;
        this.trainerDtoList = trainerDtoList;
    }
}
