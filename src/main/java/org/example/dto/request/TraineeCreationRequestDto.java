package org.example.dto.request;

import java.util.Date;
import java.util.Objects;

public class TraineeCreationRequestDto {

    private Long userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    private Date dateOfBirth;
    private String address;

    public TraineeCreationRequestDto(String firstName, String lastName, boolean isActive, Date dateOfBirth,
                                     String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.isActive = isActive;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraineeCreationRequestDto that = (TraineeCreationRequestDto) o;
        return isActive == that.isActive && Objects.equals(userId, that.userId)
            && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
            && Objects.equals(username, that.username) && Objects.equals(password, that.password)
            && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, lastName, username, password, isActive, dateOfBirth, address);
    }

    @Override
    public String toString() {
        return ("TraineeCreationRequestDto{userId=%d, firstName='%s', lastName='%s', username='%s', password='%s', "
            + "isActive=%s, dateOfBirth=%s, address='%s'}").formatted(
            userId, firstName, lastName, username, password, isActive, dateOfBirth, address);
    }
}
