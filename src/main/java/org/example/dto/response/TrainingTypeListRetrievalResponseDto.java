package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainingTypeDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingTypeListRetrievalResponseDto {

    private List<TrainingTypeDto> trainingTypes;

    public TrainingTypeListRetrievalResponseDto(List<TrainingTypeDto> trainingTypes) {
        this.trainingTypes = trainingTypes;
    }
}
