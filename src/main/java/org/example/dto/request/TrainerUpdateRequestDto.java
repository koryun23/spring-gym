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
public class TrainerUpdateRequestDto {

    private String updaterUsername;
    private String updaterPassword;

    private Long trainerId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
    private Long trainingTypeId;

    /**
     * Constructor.
     */
    public TrainerUpdateRequestDto(String updaterUsername, String updaterPassword, Long trainerId, String firstName,
                                   String lastName, String username, String password,
                                   boolean isActive, Long trainingTypeId) {
        this.updaterUsername = updaterUsername;
        this.updaterPassword = updaterPassword;
        this.trainerId = trainerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.trainingTypeId = trainingTypeId;
    }
}
