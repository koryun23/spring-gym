package org.example.entity;

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
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "TRAINER")
public class TrainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINER_GENERATOR")
    @SequenceGenerator(name = "TRAINER_GENERATOR")
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private UserEntity user;

    @Column(name = "specialization", nullable = false)
    private TrainingTypeEntity specialization;

    /**
     * No-arg constructor.
     */
    public TrainerEntity() {

    }

    /**
     * Constructor.
     */
    public TrainerEntity(Long id, UserEntity user, TrainingTypeEntity specialization) {
        this.id = id;
        this.user = user;
        this.specialization = specialization;
    }
}
