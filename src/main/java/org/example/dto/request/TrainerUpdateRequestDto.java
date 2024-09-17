package org.example.dto.request;

import java.util.Objects;
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
public class TrainerUpdateRequestDto {

    private Long trainerId;
    private Long userId;
    private Boolean isActive;
    private Long trainingTypeId;

    /**
     * Constructor.
     */
    public TrainerUpdateRequestDto(Long trainerId, Long userId, boolean isActive, Long trainingTypeId) {
        this.trainerId = trainerId;
        this.userId = userId;
        this.isActive = isActive;
        this.trainingTypeId = trainingTypeId;
    }
}
