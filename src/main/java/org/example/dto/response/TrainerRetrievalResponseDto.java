package org.example.dto.response;

import java.util.List;
import java.util.Objects;
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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainerRetrievalResponseDto that = (TrainerRetrievalResponseDto) o;
        return isActive == that.isActive && Objects.equals(userId, that.userId) &&
            Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) &&
            Objects.equals(username, that.username) && Objects.equals(password, that.password) &&
            specializationType == that.specializationType && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, username, password, isActive, specializationType, errors);
    }

    @Override
    public String toString() {
        return "TrainerRetrievalResponseDto{" +
            "userId=" + userId +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", isActive=" + isActive +
            ", specializationType=" + specializationType +
            ", errors=" + errors +
            '}';
    }
}
