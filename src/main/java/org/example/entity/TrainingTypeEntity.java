package org.example.entity;

import java.util.List;
import jakarta.persistence.Transient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
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
@Table(name = "TRAINING_TYPE")
public class TrainingTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_TYPE_SEQUENCE")
    @SequenceGenerator(name = "TRAINING_TYPE_SEQUENCE", allocationSize = 1)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "training_type")
    private TrainingType trainingType;

    @OneToMany(mappedBy = "trainingType", cascade = CascadeType.ALL)
    private List<TrainingEntity> trainingEntityList;

    @OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
    private List<TrainerEntity> trainerEntityList;

    public TrainingTypeEntity(TrainingType trainingType) {
        this.trainingType = trainingType;
    }
}
