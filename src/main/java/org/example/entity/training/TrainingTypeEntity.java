package org.example.entity.training;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.trainer.TrainerEntity;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TRAINING_TYPE")
public class TrainingTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_TYPE_SEQUENCE")
    @SequenceGenerator(name = "TRAINING_TYPE_SEQUENCE", allocationSize = 1)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "training_type")
    private TrainingType trainingType;

    @OneToMany(mappedBy = "trainingType")
    private List<TrainingEntity> trainingEntityList;

    @OneToMany(mappedBy = "specialization")
    private List<TrainerEntity> trainerEntityList;

    public TrainingTypeEntity(TrainingType trainingType) {
        this.trainingType = trainingType;
    }

    @Override
    public String toString() {
        return "TrainingTypeEntity{"
            + "trainingType=" + trainingType
            + ", id=" + id
            + '}';
    }
}
