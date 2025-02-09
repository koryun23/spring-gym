package com.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TrainerWorkingHoursResponseDto {

    @ToString.Exclude
    private String trainerUsername;
    private Long duration;

    public TrainerWorkingHoursResponseDto(String trainerUsername, Long duration) {
        setTrainerUsername(trainerUsername);
        setDuration(duration);
    }

    /**
     * Trainer username setter.
     *
     * @param trainerUsername String
     */
    public void setTrainerUsername(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainer username must not be null");
        Assert.hasText(trainerUsername, "Trainer username must not be empty");
        this.trainerUsername = trainerUsername;
    }

    /**
     * Duration setter.
     *
     * @param duration Long
     */
    public void setDuration(Long duration) {
        Assert.notNull(duration, "Duration must not be null");
        if (duration < 1) {
            throw new IllegalArgumentException("Duration must be positive");
        }
        this.duration = duration;
    }
}
