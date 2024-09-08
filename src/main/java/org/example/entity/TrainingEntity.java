package org.example.entity;

import java.util.Date;
import java.util.Objects;

public class TrainingEntity {

    private Long trainingId;
    private Long traineeId;
    private Long trainerId;
    private String name;
    private TrainingType trainingType;
    private Date trainingDate;
    private Long duration;

    /**
     * Constructor.
     */
    public TrainingEntity(Long trainingId, Long traineeId, Long trainerId, String name, TrainingType trainingType,
                          Date trainingDate, Long duration) {
        this.trainingId = trainingId;
        this.traineeId = traineeId;
        this.trainerId = trainerId;
        this.name = name;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Long getTraineeId() {
        return traineeId;
    }

    public void setTraineeId(Long traineeId) {
        this.traineeId = traineeId;
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
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainingEntity that = (TrainingEntity) o;
        return Objects.equals(trainingId, that.trainingId)
            && Objects.equals(traineeId, that.traineeId) && Objects.equals(trainerId, that.trainerId)
            && Objects.equals(name, that.name) && trainingType == that.trainingType
            && Objects.equals(trainingDate, that.trainingDate) && Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trainingId, traineeId, trainerId, name, trainingType, trainingDate, duration);
    }

    @Override
    public String toString() {
        return ("TrainingEntity{trainingId=%d, traineeId=%d, trainerId=%d, name='%s', trainingType=%s, "
            + "trainingDate=%s, duration=%d}").formatted(
            trainingId, traineeId, trainerId, name, trainingType, trainingDate, duration);
    }
}
