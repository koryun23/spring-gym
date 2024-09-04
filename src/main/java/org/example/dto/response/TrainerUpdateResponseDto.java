package org.example.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.SpecializationType;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TrainerUpdateResponseDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private SpecializationType specializationType;

    private List<String> errors;

    public TrainerUpdateResponseDto(Long userId,
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

    public TrainerUpdateResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
