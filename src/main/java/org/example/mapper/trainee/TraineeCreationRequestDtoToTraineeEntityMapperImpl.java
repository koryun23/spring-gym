package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;

@Component
public class TraineeCreationRequestDtoToTraineeEntityMapperImpl
    implements TraineeCreationRequestDtoToTraineeEntityMapper {

    @Override
    public TraineeEntity map(TraineeCreationRequestDto requestDto) {
        return new TraineeEntity(
            requestDto.getUserId(),
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            requestDto.getPassword(),
            requestDto.isActive(),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }
}
