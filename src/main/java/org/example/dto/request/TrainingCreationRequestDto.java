package org.example.dto.request;

import org.example.entity.Training;
import org.example.entity.TrainingType;

import java.util.Date;
import java.util.Objects;

public class TrainingCreationRequestDto {

    private Long traineeId;
    private Long trainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;

    public TrainingCreationRequestDto(Long traineeId, Long trainerId, String name, TrainingType trainingType, Date trainingDate, Long duration) {
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
        traineeId = traineeId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrainingCreationRequestDto training = (TrainingCreationRequestDto) o;
        return Objects.equals(traineeId, training.traineeId) && Objects.equals(trainerId, training.trainerId) && Objects.equals(name, training.name) && trainingType == training.trainingType && Objects.equals(trainingDate, training.trainingDate) && Objects.equals(duration, training.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(traineeId, trainerId, name, trainingType, trainingDate, duration);
    }

    @Override
    public String toString() {
        return "Training{" +
                ", traineeId=" + traineeId +
                ", trainerId=" + trainerId +
                ", name='" + name + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", duration=" + duration +
                '}';
    }
}
