package org.example.mapper.trainee;

import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeUpdateRequestDtoToTraineeEntityMapperImpl implements TraineeUpdateRequestDtoToTraineeEntityMapper {

    @Override
    public TraineeEntity map(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
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
