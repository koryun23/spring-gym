package com.example.dto;

import java.sql.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class TrainerWorkingHoursRequestDto {

    private String trainerUsername;
    private String trainerFirstName;
    private String trainerLastName;
    private Boolean isActive;
    private Date date;
    private Long duration;
    private ActionType actionType;

    /**
     * Constructor.
     */
    public TrainerWorkingHoursRequestDto(String trainerUsername,
                                         String trainerFirstName,
                                         String trainerLastName,
                                         Boolean isActive,
                                         Date date,
                                         Long duration,
                                         ActionType actionType) {
        setTrainerUsername(trainerUsername);
        setTrainerFirstName(trainerFirstName);
        setTrainerLastName(trainerLastName);
        setActive(isActive);
        setDate(date);
        setDuration(duration);
        setActionType(actionType);
    }

    /**
     * Trainer Username setter.
     */
    public void setTrainerUsername(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainer Username must not be null");
        Assert.hasText(trainerUsername, "Trainer Username must not be empty");
        this.trainerUsername = trainerUsername;
    }

    /**
     * Trainer First Name setter.
     */
    public void setTrainerFirstName(String trainerFirstName) {
        Assert.notNull(trainerFirstName, "Trainer First Name must not be null");
        Assert.hasText(trainerFirstName, "Trainer First Name must not be empty");
        this.trainerFirstName = trainerFirstName;
    }

    /**
     * Trainer Last Name setter.
     */
    public void setTrainerLastName(String trainerLastName) {
        Assert.notNull(trainerLastName, "Trainer Last Name must not be null");
        Assert.hasText(trainerLastName, "Trainer Last Name must not be empty");
        this.trainerLastName = trainerLastName;
    }

    /**
     * A setter for the is active field.
     */
    public void setActive(Boolean active) {
        Assert.notNull(isActive, "Is Active field must not be null");
        isActive = active;
    }

    /**
     * Training Date setter.
     */
    public void setDate(Date date) {
        Assert.notNull(date, "Training Date must not be null");
        this.date = date;
    }

    /**
     * Training Duration setter, in milliseconds.
     */
    public void setDuration(Long duration) {
        Assert.notNull(duration, "Training Duration must not be null");
        if (duration < 0) {
            throw new IllegalArgumentException("Training Duration must be positive");
        }
        this.duration = duration;
    }

    /**
     * Action Type setter.
     */
    public void setActionType(ActionType actionType) {
        Assert.notNull(actionType, "Action type must not be null - either ADD or DELETE");
        this.actionType = actionType;
    }
}
