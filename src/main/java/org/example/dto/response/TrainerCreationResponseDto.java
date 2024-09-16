package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainerCreationResponseDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private TrainingType specializationType;

    private List<String> errors;

    /**
     * Constructor.
     */
    public TrainerCreationResponseDto(Long userId,
                                      String firstName,
                                      String lastName,
                                      String username,
                                      String password,
                                      boolean isActive,
                                      TrainingType specializationType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.specializationType = specializationType;
    }

    /**
     * Constructor.
     */
    public TrainerCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
