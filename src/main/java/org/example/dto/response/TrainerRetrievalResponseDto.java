package org.example.dto.response;

import java.util.List;
import java.util.Objects;
import org.example.entity.TrainingType;

public class TrainerRetrievalResponseDto {

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
    public TrainerRetrievalResponseDto(Long userId,
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

    public TrainerRetrievalResponseDto(List<String> errors) {
        this.errors = errors;
    }

}
