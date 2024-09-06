package org.example.mapper.trainee;

import org.example.dto.response.TraineeCreationResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;

@Component
public class TraineeEntityToTraineeCreationResponseDtoMapperImpl
    implements TraineeEntityToTraineeCreationResponseDtoMapper {

    @Override
    public TraineeCreationResponseDto map(TraineeEntity trainee) {
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
