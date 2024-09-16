package org.example.dto.request;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.TrainingType;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainerUpdateRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private TrainingType specializationType;

    /**
     * Constructor.
     */
    public TrainerUpdateRequestDto(Long userId, String firstName, String lastName, String username, String password,
                                   boolean isActive, TrainingType specializationType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.specializationType = specializationType;
    }

}
