package org.example.entity;

import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class TrainerEntity extends User {

    private SpecializationType specialization;
    private Long userId;

    public TrainerEntity(Long userId,
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        TrainerEntity that = (TrainerEntity) o;
        return specialization == that.specialization && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), specialization, userId);
    }

    @Override
    public String toString() {
        return "TrainerEntity{" +
            "specialization=" + specialization +
            ", userId=" + userId +
            '}';
    }
}
