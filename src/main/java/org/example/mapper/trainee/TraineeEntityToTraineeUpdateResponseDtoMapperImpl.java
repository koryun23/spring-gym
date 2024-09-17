package org.example.mapper.trainee;

import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeEntityToTraineeUpdateResponseDtoMapperImpl
    implements TraineeEntityToTraineeUpdateResponseDtoMapper {

    @Override
    public TraineeUpdateResponseDto map(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeUpdateResponseDto(
            trainee.getId(),
            trainee.getUser().getIsActive(),
            trainee.getDateOfBirth(),
            trainee.getAddress()
        );
    }
}
