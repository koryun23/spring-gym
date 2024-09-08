package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeCreationRequestDtoToTraineeEntityMapperImpl
    implements TraineeCreationRequestDtoToTraineeEntityMapper {

    @Override
    public TraineeEntity map(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
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
