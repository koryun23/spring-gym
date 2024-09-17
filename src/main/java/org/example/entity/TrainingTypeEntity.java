package org.example.entity;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TRAINING_TYPE")
public class TrainingTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_TYPE_SEQUENCE")
    @SequenceGenerator(name = "TRAINING_TYPE_SEQUENCE")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @JoinColumn(name = "training_type")
    @ManyToOne
    private TrainingType trainingType;

    /**
     * No-arg constructor.
     */
    public TrainingTypeEntity() {
    }

    /**
     * Constructor.
     */
    public TrainingTypeEntity(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TrainingType getTrainingType() {
        return trainingType;
    }

    public void setTrainingType(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TrainingTypeEntity that = (TrainingTypeEntity) o;
        return Objects.equals(id, that.id) && trainingType == that.trainingType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trainingType);
    }

    @Override
    public String toString() {
        return "TrainingTypeEntity{"
            + "id=" + id
            + ", trainingType=" + trainingType
            + '}';
    }
}
