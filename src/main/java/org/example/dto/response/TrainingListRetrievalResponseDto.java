package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingListRetrievalResponseDto {

    private Boolean status;
    private List<TrainingRetrievalResponseDto> trainingList;

    private List<String> errors;

    public TrainingListRetrievalResponseDto(Boolean status, List<TrainingRetrievalResponseDto> trainingList) {
        this.status = status;
        this.trainingList = trainingList;
    }

    public TrainingListRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
