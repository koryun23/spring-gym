package org.example.mapper.trainee;

import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;

@Component
public class TraineeEntityToTraineeUpdateResponseDtoMapperImpl
    implements TraineeEntityToTraineeUpdateResponseDtoMapper {
    @Override
    public TraineeUpdateResponseDto map(TraineeEntity trainee) {
        return new TraineeUpdateResponseDto(
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
