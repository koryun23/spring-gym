package org.example.mapper.trainee;

import org.example.dao.core.UserDao;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.entity.TraineeEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeUpdateRequestDtoToTraineeEntityMapperImpl implements TraineeUpdateRequestDtoToTraineeEntityMapper {

    private final UserDao userDao;

    public TraineeUpdateRequestDtoToTraineeEntityMapperImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public TraineeEntity map(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new TraineeEntity(
            userDao.getByUsername(requestDto.getUsername()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }
}
