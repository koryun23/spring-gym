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
    public TraineeUpdateRequestDto(String firstName,
                                   String lastName,
                                   String username,
                                   Boolean isActive,
                                   Date dateOfBirth,
                                   String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
