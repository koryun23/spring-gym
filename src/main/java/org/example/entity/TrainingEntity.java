package org.example.entity;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TRAINING")
public class TrainingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINING_SEQUENCE")
    @SequenceGenerator(name = "TRAINING_SEQUENCE")
    private Long id;

    @JoinColumn()
    private TraineeEntity trainee;
    private TrainerEntity trainer;
    private String name;
    private TrainingTypeEntity trainingType;
    private Date date;
    private Long duration;


}
