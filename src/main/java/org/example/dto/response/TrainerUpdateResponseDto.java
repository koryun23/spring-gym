package org.example.dto.response;

import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainingType;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerUpdateResponseDto {

    private Long trainerId;
    private Long userId;
    private boolean isActive;
    private Long trainingTypeId;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerUpdateResponseDto(Long trainerId,
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
    public TrainerUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
