package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINER_SEQUENCE")
    @SequenceGenerator(name = "TRAINER_SEQUENCE", allocationSize = 1)
    private Long id;

    //TODO  - Problem: Using EAGER fetching for UserEntity.
    // Why: EAGER fetching can lead to performance issues by loading all associated data immediately,
    // resulting in unnecessary data retrieval and increased memory usage. This can slow down
    // the application, especially if there are many entities involved or if the association is
    // not always needed.
    // What I offer: Consider switching to LAZY fetching, allowing data to be loaded only when
    // it's actually needed, which can improve performance and reduce memory overhead.
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
    @ManyToOne
    private TrainingTypeEntity specialization;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer")
    private List<TrainingEntity> trainingEntityList;

    @Transient
    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "trainerEntityList")
    private List<TraineeEntity> traineeEntityList;

    /**
     * Constructor.
     */
    public TrainerEntity(UserEntity user, TrainingTypeEntity specialization) {
        this.user = user;
        this.specialization = specialization;
    }
}
