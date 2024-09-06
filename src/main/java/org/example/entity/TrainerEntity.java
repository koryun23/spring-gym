package org.example.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
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
}
