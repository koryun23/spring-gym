package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainerDto;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TraineeUpdateResponseDto {

    @ToString.Exclude
    private String username;

    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    private String address;
    private Boolean isActive;
    private List<TrainerDto> trainers;
}
