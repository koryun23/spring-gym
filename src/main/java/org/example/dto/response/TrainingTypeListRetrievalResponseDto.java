package org.example.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.dto.plain.TrainingTypeDto;
import org.example.entity.TrainingType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TrainingTypeListRetrievalResponseDto {

    private List<TrainingType> trainingTypes;
}
