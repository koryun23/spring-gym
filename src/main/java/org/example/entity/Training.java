package org.example.entity;

import java.util.Date;
import java.util.Objects;

public class Training {

    private Long TraineeId;
    private Long TrainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;

    public Training(Long traineeId, Long trainerId, String name, TrainingType trainingType, Date trainingDate, Long duration) {
        TraineeId = traineeId;
        TrainerId = trainerId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }

    public Long getTraineeId() {
        return TraineeId;
    }

    public void setTraineeId(Long traineeId) {
        TraineeId = traineeId;
    }

    public Long getTrainerId() {
        return TrainerId;
    }

    public void setTrainerId(Long trainerId) {
        TrainerId = trainerId;
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
        Training training = (Training) o;
        return Objects.equals(TraineeId, training.TraineeId) && Objects.equals(TrainerId, training.TrainerId) && Objects.equals(name, training.name) && trainingType == training.trainingType && Objects.equals(trainingDate, training.trainingDate) && Objects.equals(duration, training.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TraineeId, TrainerId, name, trainingType, trainingDate, duration);
    }

    @Override
    public String toString() {
        return "Training{" +
                "TraineeId=" + TraineeId +
                ", TrainerId=" + TrainerId +
                ", name='" + name + '\'' +
                ", trainingType=" + trainingType +
                ", trainingDate=" + trainingDate +
                ", duration=" + duration +
                '}';
    }
}
