package org.example.dto.response;

import java.util.Objects;

public class TraineeDeletionResponseDto {

    private boolean status;

    public TraineeDeletionResponseDto() {
    }

    public TraineeDeletionResponseDto(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TraineeDeletionResponseDto that = (TraineeDeletionResponseDto) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(status);
    }

    @Override
    public String toString() {
        return "TraineeDeletionResponseDto{" +
                "status=" + status +
                '}';
    }
}
