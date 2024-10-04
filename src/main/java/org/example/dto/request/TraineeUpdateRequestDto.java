package org.example.dto.request;

import java.sql.Date;
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

    private String updaterUsername;
    private String updaterPassword;

    private Long traineeId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Boolean isActive;
    private Date dateOfBirth;
    private String address;

    /**
     * Constructor.
     */
    public TraineeUpdateRequestDto(String updaterUsername,
                                   String updaterPassword,
                                   Long traineeId,
                                   String firstName,
                                   String lastName,
                                   String username,
                                   String password,
                                   Boolean isActive,
                                   Date dateOfBirth,
                                   String address) {
        this.updaterUsername = updaterUsername;
        this.updaterPassword = updaterPassword;
        this.traineeId = traineeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
