package org.example.dto.response;

import java.sql.Date;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainerDto;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeUpdateResponseDto {

    private String username;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String address;
    private Boolean isActive;
    private List<TrainerDto> trainers;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TraineeUpdateResponseDto(String username,
                                    String firstName,
                                    String lastName,
                                    Date dateOfBirth,
                                    String address,
                                    Boolean isActive,
                                    List<TrainerDto> trainers) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.isActive = isActive;
        this.trainers = trainers;
    }

    public TraineeUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
