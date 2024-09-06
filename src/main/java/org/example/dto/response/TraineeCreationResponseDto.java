package org.example.dto.response;

import java.util.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TraineeCreationResponseDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private Date dateOfBirth;
    private String address;

    private List<String> errors;

    public TraineeCreationResponseDto(Long userId,
                                      String firstName,
                                      String lastName,
                                      String username,
                                      String password,
                                      boolean isActive,
                                      Date dateOfBirth,
                                      String address) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    public TraineeCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
