package com.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINER_ENTITY_SEQUENCE")
    @SequenceGenerator(name = "TRAINER_ENTITY_SEQUENCE", allocationSize = 1)
    private Long trainerId;

    @Column(name = "trainer_username", nullable = false)
    private String trainerUsername;

    @Column(name = "trainer_first_name", nullable = false)
    private String trainerFirstName;

    @Column(name = "trainer_last_name", nullable = false)
    private String trainerLastName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "training_year")
    private Integer trainingYear;

    @Column(name = "training_month")
    private Integer trainingMonth;

    @Column(name = "duration")
    private Long duration;

    /**
     * Constructor.
     */
    public TrainerEntity(String trainerUsername,
                         String trainerFirstName,
                         String trainerLastName,
                         Boolean isActive,
                         Integer trainingYear,
                         Integer trainingMonth,
                         Long duration) {
        this.trainerUsername = trainerUsername;
        this.trainerFirstName = trainerFirstName;
        this.trainerLastName = trainerLastName;
        this.isActive = isActive;
        this.trainingYear = trainingYear;
        this.trainingMonth = trainingMonth;
        this.duration = duration;
    }
}
