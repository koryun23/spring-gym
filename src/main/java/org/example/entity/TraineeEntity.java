package org.example.entity;

import jakarta.persistence.ForeignKey;
import java.util.Date;
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
@Table(name = "TRAINEE")
public class TraineeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRAINEE_GENERATOR")
    @SequenceGenerator(name = "TRAINEE_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "address", nullable = false)
    private String address;

    /**
     * No-arg constructor.
     */
    public TraineeEntity() {
    }

    /**
     * Constructor.
     */
    public TraineeEntity(Long id, UserEntity user, Date dateOfBirth, String address) {
        this.id = id;
        this.user = user;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
