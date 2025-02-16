package com.example.entity;

import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "trainer_entity")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerEntity {

    @Id
    private Long trainerId;

    @Indexed(name = "trainer_username", unique = true)
    private String trainerUsername;

    @Field(name = "trainer_first_name")
    private String trainerFirstName;

    @Field(name = "trainer_last_name")
    private String trainerLastName;

    @Field(name = "is_active")
    private Boolean isActive;

    @Field(name = "training_year")
    private Integer trainingYear;

    @Field(name = "training_month")
    private Integer trainingMonth;

    @Field(name = "duration")
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
