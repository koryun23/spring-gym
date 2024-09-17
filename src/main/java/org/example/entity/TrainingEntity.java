package org.example.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
    @SequenceGenerator(name = "TRAINING_SEQUENCE")
    private Long id;

    @JoinColumn(name = "trainee_id", referencedColumnName = "id")
    @ManyToOne
    private TraineeEntity trainee;

    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    @ManyToOne
    private TrainerEntity trainer;

    @Column(name = "training_name", nullable = false)
    private String name;

    @JoinColumn(name = "training_type_id", nullable = false)
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
