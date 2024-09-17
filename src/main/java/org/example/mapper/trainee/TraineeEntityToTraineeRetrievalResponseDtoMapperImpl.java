package org.example.mapper.trainee;

import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeEntityToTraineeRetrievalResponseDtoMapperImpl
    implements TraineeEntityToTraineeRetrievalResponseDtoMapper {

    @Override
    public TraineeRetrievalResponseDto map(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeRetrievalResponseDto(
            trainee.getId(),
            trainee.getUser().getId(),
            trainee.getUser().getIsActive(),
            trainee.getDateOfBirth(),
            trainee.getAddress()
        );
    }
}
