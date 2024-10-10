package org.example.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.sql.Date;
import java.util.List;
import java.util.Set;
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
@Table(name = "TRAINEE")
public class TraineeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINEE_SEQUENCE")
    @SequenceGenerator(name = "TRAINEE_SEQUENCE", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "address", nullable = false)
    private String address;

    @Transient
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainee")
    private List<TrainingEntity> trainingEntityList;

    @ManyToMany
    @JoinTable(
        name = "trainee_trainer",
        joinColumns = @JoinColumn(name = "trainee_id"),
        inverseJoinColumns = @JoinColumn(name = "trainer_id")
    )
    private Set<TrainerEntity> trainerEntities;

    /**
     * Constructor.
     */
    public TraineeEntity(UserEntity user, Date dateOfBirth, String address) {
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
