package org.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainerUpdateRequestDto {

    @ToString.Exclude
    private String username;

    private String firstName;
    private String lastName;
    private Long specializationId;
    private Boolean isActive;

}
