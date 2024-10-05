package org.example.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainingTypeDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerUpdateRequestDto {

    private String updaterUsername;
    private String updaterPassword;

    private String username;
    private String firstName;
    private String lastName;
    private TrainingTypeDto specialization;
    private Boolean isActive;

    /**
     * Constructor.
     */
    public TrainerUpdateRequestDto(String username,
                                   String firstName,
                                   String lastName,
                                   TrainingTypeDto specialization,
                                   Boolean isActive) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.specialization = specialization;
        this.isActive = isActive;
    }
}
