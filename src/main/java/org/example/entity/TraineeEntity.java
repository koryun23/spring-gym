package org.example.entity;

import java.util.Date;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TraineeEntity extends User {

    private Date dateOfBirth;
    private String address;
    private Long userId;

    public TraineeEntity(Long userId,
                         String firstName,
                         String lastName,
                         String username,
                         String password,
                         boolean isActive,
                         Date dateOfBirth,
                         String address) {
        super(firstName, lastName, username, password, isActive);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.userId = userId;
    }
}
