package org.example.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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
@Table(name = "TRAINER")
public class TrainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINER_GENERATOR")
    @SequenceGenerator(name = "TRAINER_GENERATOR")
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private TrainingTypeEntity specialization;
    /**
     * Constructor.
     */
    public TrainerEntity(UserEntity user, TrainingTypeEntity specialization) {
        this.user = user;
        this.specialization = specialization;
    }
}
