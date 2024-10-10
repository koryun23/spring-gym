package org.example.mapper.trainee;

import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.example.service.core.UserService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeCreationRequestDtoToTraineeEntityMapperImpl
    implements TraineeCreationRequestDtoToTraineeEntityMapper {

    private UserService userDao;

    public TraineeCreationRequestDtoToTraineeEntityMapperImpl(UserService userDao) {
        this.userDao = userDao;
    }

    @Override
    public TraineeEntity map(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new TraineeEntity(
            userDao.getByUsername(requestDto.getUsername()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }
}
