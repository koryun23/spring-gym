package org.example.mapper.trainer;

import org.example.dao.core.TrainingTypeDao;
import org.example.dao.core.UserDao;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerUpdateRequestDtoToTrainerEntityMapperImpl implements TrainerUpdateRequestDtoToTrainerEntityMapper {

    private final UserDao userDao;
    private final TrainingTypeDao trainingTypeDao;

    public TrainerUpdateRequestDtoToTrainerEntityMapperImpl(UserDao userDao, TrainingTypeDao trainingTypeDao) {
        this.userDao = userDao;
        this.trainingTypeDao = trainingTypeDao;
    }


    @Override
    public TrainerEntity map(TrainerUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerUpdateRequestDto must not be null");

        return new TrainerEntity(
            userDao.getByUsername(requestDto.getUsername()),
            trainingTypeDao.get(requestDto.getTrainingTypeId())
        );
    }
}
