package org.example.dto.response;

import java.util.Date;
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
public class TraineeRetrievalResponseDto {

    private Long traineeId;
    private Long userId;
    private boolean isActive;
    private Date dateOfBirth;
    private String address;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TraineeRetrievalResponseDto(Long traineeId,
                                       Long userId,
                                       boolean isActive,
                                       Date dateOfBirth,
                                       String address) {
        this.traineeId = traineeId;
        this.userId = userId;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    /**
     * Constructor.
     */
    public TraineeRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
