package org.example.dto.request;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeUpdateRequestDto {

    private String firstName;
    private String lastName;

    @ToString.Exclude
    private String username;

    private Boolean isActive;
    private Date dateOfBirth;
    private String address;

}
