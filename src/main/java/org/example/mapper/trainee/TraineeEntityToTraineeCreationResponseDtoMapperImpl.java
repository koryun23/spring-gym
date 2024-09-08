package org.example.mapper.trainee;

import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeEntityToTraineeCreationResponseDtoMapperImpl
    implements TraineeEntityToTraineeCreationResponseDtoMapper {

    @Override
    public TraineeCreationResponseDto map(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeCreationResponseDto(
            trainee.getUserId(),
            trainee.getFirstName(),
            trainee.getLastName(),
            trainee.getUsername(),
            trainee.getPassword(),
            trainee.isActive(),
            trainee.getDateOfBirth(),
            trainee.getAddress()
        );
    }
}
