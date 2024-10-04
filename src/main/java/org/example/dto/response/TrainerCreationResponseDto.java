package org.example.dto.response;

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
public class TrainerCreationResponseDto {

    private String firstName;
    private String lastName;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerCreationResponseDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructor.
     */
    public TrainerCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
