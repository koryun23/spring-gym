package org.example.entity.trainer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.LinkedList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.entity.training.TrainingEntity;
import org.example.entity.training.TrainingTypeEntity;
import org.example.entity.user.UserEntity;

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

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @OneToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @JoinColumn(name = "specialization_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private TrainingTypeEntity specialization;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainer")
    private List<TrainingEntity> trainingEntityList = new LinkedList<>();

    /**
     * Constructor.
     */
    public TrainerEntity(UserEntity user, TrainingTypeEntity specialization) {
        this.user = user;
        this.specialization = specialization;
    }
}
