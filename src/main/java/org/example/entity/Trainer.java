package org.example.entity;


import java.util.Objects;

public class Trainer extends User {

    private SpecializationType specialization;
    private Long userId;

    public Trainer(Long userId,
                   String firstName,
                   String lastName,
                   String username,
                   String password,
                   boolean isActive,
                   SpecializationType specialization) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    public SpecializationType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationType specialization) {
        this.specialization = specialization;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Trainer trainer = (Trainer) o;
        return specialization == trainer.specialization && Objects.equals(userId, trainer.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization, userId);
    }

    @Override
    public String toString() {
        return "Trainer{" +
                "specialization='" + specialization + '\'' +
                ", userId=" + userId +
                '}';
    }
}
