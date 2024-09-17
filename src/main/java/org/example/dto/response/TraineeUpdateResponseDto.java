package org.example.dto.response;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.request.TraineeUpdateRequestDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeUpdateResponseDto {

    private Long traineeId;
    private boolean isActive;
    private Date dateOfBirth;
    private String address;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TraineeUpdateResponseDto(Long traineeId,
                                    boolean isActive,
                                    Date dateOfBirth,
                                    String address) {
        this.traineeId = traineeId;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public TraineeUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
