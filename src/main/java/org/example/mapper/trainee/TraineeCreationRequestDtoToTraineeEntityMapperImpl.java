package org.example.mapper.trainee;

import org.example.dao.core.UserDao;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeCreationRequestDtoToTraineeEntityMapperImpl
    implements TraineeCreationRequestDtoToTraineeEntityMapper {

    private UserDao userDao;

    public TraineeCreationRequestDtoToTraineeEntityMapperImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public TraineeEntity map(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new TraineeEntity(
            userDao.get(requestDto.getUserId()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }
}
