package org.example.dto.response;

import java.util.List;
import org.example.entity.SpecializationType;

public class TrainerRetrievalResponseDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private SpecializationType specializationType;

    private List<String> errors;

    public TrainerRetrievalResponseDto(Long userId,
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

    public TrainerRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }
}
