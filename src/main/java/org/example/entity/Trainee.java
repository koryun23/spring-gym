package org.example.entity;

import java.util.Date;
import java.util.Objects;

public class Trainee extends User {

    private Date dateOfBirth;
    private String address;
    private Long userId;

    public Trainee(Long userId,
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainee trainee = (Trainee) o;
        return Objects.equals(dateOfBirth, trainee.dateOfBirth) && Objects.equals(address, trainee.address) && Objects.equals(userId, trainee.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateOfBirth, address, userId);
    }

    @Override
    public String toString() {
        return "Trainee{" +
                "dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                '}';
    }
}
