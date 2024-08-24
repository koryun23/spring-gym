package org.example.entity;


import java.util.Objects;

public class Trainer extends User {

    private String specialization;
    private Long userId;

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, Long userId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(specialization, trainer.specialization) && Objects.equals(userId, trainer.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(specialization, userId);
    }
}
