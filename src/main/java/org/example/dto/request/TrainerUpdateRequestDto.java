package org.example.dto.request;

import java.util.Objects;
import org.example.entity.SpecializationType;

public class TrainerUpdateRequestDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private SpecializationType specializationType;

    /**
     * Constructor.
     */
    public TrainerUpdateRequestDto(Long userId, String firstName, String lastName, String username, String password,
                                   boolean isActive, SpecializationType specializationType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
        this.specializationType = specializationType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public SpecializationType getSpecializationType() {
        return specializationType;
    }

    public void setSpecializationType(SpecializationType specializationType) {
        this.specializationType = specializationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainerUpdateRequestDto that = (TrainerUpdateRequestDto) o;
        return isActive == that.isActive && Objects.equals(userId, that.userId)
            && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
            && Objects.equals(username, that.username) && Objects.equals(password, that.password)
            && specializationType == that.specializationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, username, password, isActive, specializationType);
    }

    @Override
    public String toString() {
        return ("TrainerUpdateRequestDto{userId=%d, firstName='%s', lastName='%s', username='%s', password='%s', "
            + "isActive=%s, specializationType=%s}").formatted(
            userId, firstName, lastName, username, password, isActive, specializationType);
    }
}
