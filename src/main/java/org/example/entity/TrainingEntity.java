package org.example.entity;

import java.util.Date;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "TRAINING")
public class TrainingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_SEQUENCE")
    @SequenceGenerator(name = "TRAINING_SEQUENCE", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "trainee_id", referencedColumnName = "id")
    @ManyToOne
    private TraineeEntity trainee;

    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    @ManyToOne
    private TrainerEntity trainer;

    @Column(name = "training_name", nullable = false)
    private String name;

    @JoinColumn(name = "training_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private TrainingTypeEntity trainingType;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "duration", nullable = false)
    private Long duration;

    /**
     * Constructor.
     */
    public TrainingEntity(TraineeEntity trainee, TrainerEntity trainer, String name,
                          TrainingTypeEntity trainingType, Date date, Long duration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.name = name;
        this.trainingType = trainingType;
        this.date = date;
        this.duration = duration;
    }
}
