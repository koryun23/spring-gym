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
public class TrainerRetrievalResponseDto {

    private Long trainerId;
    private Long userId;
    private boolean isActive;
    private Long trainingTypeId;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerRetrievalResponseDto(Long trainerId,
                                       Long userId,
                                       boolean isActive,
                                       Long trainingTypeId) {
        this.trainerId = trainerId;
        this.userId = userId;
        this.isActive = isActive;
        this.trainingTypeId = trainingTypeId;
    }

    /**
     * Constructor.
     */
    public TrainerRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
