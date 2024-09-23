package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TraineePasswordChangeRequestDto {

    private String updaterUsername;
    private String updaterPassword;
    private Long traineeId;
    private String newPassword;

}
