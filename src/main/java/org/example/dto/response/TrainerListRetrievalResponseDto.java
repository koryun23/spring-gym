package org.example.dto.response;

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
public class TrainerListRetrievalResponseDto {

    private String username;
    private String firstName;
    private String lastName;
    private List<TrainerDto> trainers;

    private List<String> errors;

    public TrainerListRetrievalResponseDto(List<TrainerDto> trainers, String username) {
        this.trainers = trainers;
        this.username = username;
    }

    public TrainerListRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
