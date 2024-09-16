package org.example.dto.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainerCreationRequestDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private TrainingType specializationType;

    /**
     * Constructor.
     */
    public TrainerCreationRequestDto(String firstName,
                                     String lastName,
                                     boolean isActive,
                                     TrainingType specializationType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.specializationType = specializationType;
    }
}
