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
public class TrainerListRetrievalResponseDto {

    private String username;
    private List<TrainerRetrievalResponseDto> trainers;

    private List<String> errors;

    public TrainerListRetrievalResponseDto(List<TrainerRetrievalResponseDto> trainers, String username) {
        this.trainers = trainers;
        this.username = username;
    }

    public TrainerListRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
