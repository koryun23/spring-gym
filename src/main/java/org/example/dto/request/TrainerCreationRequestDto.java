package org.example.dto.request;

import org.example.entity.SpecializationType;

import java.util.Objects;

public class TrainerCreationRequestDto {

    private String firstName;
    private String lastName;
    private boolean isActive;

    private SpecializationType specializationType;

    public TrainerCreationRequestDto() {
    }

    public TrainerCreationRequestDto(String firstName,
                                     String lastName,
                                     boolean isActive,
                                     SpecializationType specializationType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.specializationType = specializationType;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainerCreationRequestDto that = (TrainerCreationRequestDto) o;
        return isActive == that.isActive &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                specializationType == that.specializationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, isActive, specializationType);
    }

    @Override
    public String toString() {
        return "TrainerCreationRequestDto{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isActive=" + isActive +
                ", specializationType=" + specializationType +
                '}';
    }
}
