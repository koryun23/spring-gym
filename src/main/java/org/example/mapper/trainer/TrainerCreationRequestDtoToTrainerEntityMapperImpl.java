package org.example.mapper.trainer;

import org.example.dao.core.TrainingTypeDao;
import org.example.dao.core.UserDao;
import org.example.dto.request.TrainerCreationRequestDto;
import org.example.entity.TrainerEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TrainerCreationRequestDtoToTrainerEntityMapperImpl
    implements TrainerCreationRequestDtoToTrainerEntityMapper {

    private final UserDao userDao;
    private final TrainingTypeDao trainingTypeDao;

    public TrainerCreationRequestDtoToTrainerEntityMapperImpl(UserDao userDao, TrainingTypeDao trainingTypeDao) {
        this.userDao = userDao;
        this.trainingTypeDao = trainingTypeDao;
    }

    @Override
    public TrainerEntity map(TrainerCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TrainerCreationRequestDto must not be null");
        return new TrainerEntity(
            userDao.getByUsername(requestDto.getUsername()),
            trainingTypeDao.get(requestDto.getTrainingTypeId())
        );
    }
}
