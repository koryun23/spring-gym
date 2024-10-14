package org.example.dto.response;

import java.util.Date;
import java.util.List;
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
public class TraineeCreationResponseDto {

    private String firstName;
    private String lastName;

    /**
     * Constructor.
     */
    public TraineeCreationResponseDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
