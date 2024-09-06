package org.example.mapper.trainee;

import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;

@Component
public class TraineeUpdateRequestDtoToTraineeEntityMapperImpl implements TraineeUpdateRequestDtoToTraineeEntityMapper {

    @Override
    public TraineeEntity map(TraineeUpdateRequestDto requestDto) {
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
