package org.example.dto.response;

import java.util.List;
import java.util.Objects;

public class TraineeDeletionResponseDto {

    private boolean status;

    private List<String> errors;

    public TraineeDeletionResponseDto(boolean status) {
        this.status = status;
    }

    public TraineeDeletionResponseDto(List<String> errors) {
        this.errors = errors;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TraineeDeletionResponseDto that = (TraineeDeletionResponseDto) o;
        return status == that.status && Objects.equals(errors, that.errors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, errors);
    }

    @Override
    public String toString() {
        return "TraineeDeletionResponseDto{status=%s, errors=%s}".formatted(status, errors);
    }
}
