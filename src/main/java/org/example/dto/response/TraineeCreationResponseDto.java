package org.example.dto.response;

import java.util.Date;
import java.util.List;
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
public class TraineeCreationResponseDto {

    private Long traineeId;
    private Long userId;
    private Boolean isActive;
    private Date dateOfBirth;
    private String address;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TraineeCreationResponseDto(Long traineeId,
                                      Long userId,
                                      Boolean isActive,
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
    public TraineeCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
