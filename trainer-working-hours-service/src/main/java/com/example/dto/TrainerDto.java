package com.example.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TrainerDto {

    @ToString.Exclude
    private String trainerUsername;

    private String trainerFirstName;
    private String trainerLastName;
    private Boolean isActive;
    private Integer trainingYear;
    private Integer trainingMonth;
    private Long duration;

    /**
     * Constructor.
     */
    public TrainerDto(String trainerUsername, String trainerFirstName, String trainerLastName,
                      Boolean isActive, Integer trainingYear, Integer trainingMonth, Long duration) {
        setTrainerUsername(trainerUsername);
        setTrainerFirstName(trainerFirstName);
        setTrainerLastName(trainerLastName);
        setActive(isActive);
        setTrainingYear(trainingYear);
        setTrainingMonth(trainingMonth);
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
     * Trainer first name setter.
     *
     * @param trainerFirstName String
     */
    public void setTrainerFirstName(String trainerFirstName) {
        Assert.notNull(trainerFirstName, "Trainer first name must not be null");
        Assert.notNull(trainerFirstName, "Trainer first name must not be empty");
        this.trainerFirstName = trainerFirstName;
    }

    /**
     * Trainer last name setter.
     *
     * @param trainerLastName String
     */
    public void setTrainerLastName(String trainerLastName) {
        Assert.notNull(trainerLastName, "Trainer last name must not be null");
        Assert.hasText(trainerLastName, "Trainer last name must not be empty");
        this.trainerLastName = trainerLastName;
    }

    /**
     * Trainer activation state setter.
     *
     * @param isActive Boolean
     */
    public void setActive(Boolean isActive) {
        Assert.notNull(isActive, "Activation state must not be null");
        this.isActive = isActive;
    }

    /**
     * Training year setter.
     *
     * @param trainingYear Integer
     */
    public void setTrainingYear(Integer trainingYear) {
        Assert.notNull(trainingYear, "Training year must not be null");
        if (trainingYear < 1) {
            throw new IllegalArgumentException("Training year must be positive");
        }
        this.trainingYear = trainingYear;
    }

    /**
     * Training month setter.
     *
     * @param trainingMonth Integer
     */
    public void setTrainingMonth(Integer trainingMonth) {
        Assert.notNull(trainingMonth, "Training month must not be null");
        if (trainingMonth < 1 || trainingMonth > 12) {
            throw new IllegalArgumentException("Training month must be in the range from 1 to 12");
        }
        this.trainingMonth = trainingMonth;
    }

    /**
     * Training duration setter.
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
