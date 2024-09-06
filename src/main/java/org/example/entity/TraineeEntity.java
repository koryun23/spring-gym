package org.example.entity;

import java.util.Date;
import java.util.Objects;

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        TraineeEntity that = (TraineeEntity) o;
        return Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(address, that.address) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfBirth, address, userId);
    }

    @Override
    public String toString() {
        return "TraineeEntity{" +
            "dateOfBirth=" + dateOfBirth +
            ", address='" + address + '\'' +
            ", userId=" + userId +
            '}';
    }
}
