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
public class TraineeCreationRequestDto {

    private Long userId;
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
    public TraineeCreationRequestDto(String firstName,
                                     String lastName,
                                     Date dateOfBirth,
                                     String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = true;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
