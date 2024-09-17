package org.example.dto.request;

import java.util.Date;
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
public class TraineeUpdateRequestDto {

    private Long traineeId;
    private Long userId;
    private Boolean isActive;
    private Date dateOfBirth;
    private String address;

    /**
     * Constructor.
     */
    public TraineeUpdateRequestDto(Long traineeId,
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
}
