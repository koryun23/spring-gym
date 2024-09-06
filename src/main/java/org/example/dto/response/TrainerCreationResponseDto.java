package org.example.dto.response;

import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.SpecializationType;

@NoArgsConstructor
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
    private SpecializationType specializationType;

    private List<String> errors;

    public TrainerCreationResponseDto(Long userId,
                                      String firstName,
                                      String lastName,
                                      String username,
                                      String password,
                                      boolean isActive,
                                      SpecializationType specializationType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.specializationType = specializationType;
    }

    public TrainerCreationResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
