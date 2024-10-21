package org.example.dto.request;

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
public class TrainerCreationRequestDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
    private Long trainingTypeId;

    /**
     * Constructor.
     */
    public TrainerCreationRequestDto(String firstName,
                                     String lastName,
                                     Long trainingTypeId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.trainingTypeId = trainingTypeId;
    }
}
