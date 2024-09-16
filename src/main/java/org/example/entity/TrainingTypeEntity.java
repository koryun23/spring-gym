package org.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "TRAINING_TYPE")
@Getter
@Setter
@EqualsAndHashCode
@ToString
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
    public TrainingTypeEntity(Long id, TrainingType trainingType) {
        this.id = id;
        this.trainingType = trainingType;
    }
}
