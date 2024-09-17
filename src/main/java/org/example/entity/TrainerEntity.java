package org.example.entity;

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
     * Constructor.
     */
    public TrainerEntity(UserEntity user, TrainingTypeEntity specialization) {
        this.user = user;
        this.specialization = specialization;
    }
}
