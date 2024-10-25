package org.example.mapper.trainee;

import java.util.List;
import java.util.stream.Collectors;
import org.example.dto.plain.TrainerDto;
import org.example.dto.plain.UserDto;
import org.example.dto.request.TraineeCreationRequestDto;
import org.example.dto.request.TraineeSwitchActivationStateRequestDto;
import org.example.dto.request.TraineeUpdateRequestDto;
import org.example.dto.response.TraineeCreationResponseDto;
import org.example.dto.response.TraineeRetrievalResponseDto;
import org.example.dto.response.TraineeUpdateResponseDto;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainingEntity;
import org.example.entity.UserEntity;
import org.example.service.core.training.TrainingService;
import org.example.service.core.user.UserService;
import org.example.service.core.user.UsernamePasswordService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TraineeMapperImpl implements TraineeMapper {

    private final UserService userService;
    private final UsernamePasswordService usernamePasswordService;
    private final TrainingService trainingService;

    /**
     * Constructor.
     */
    public TraineeMapperImpl(UserService userService,
                             UsernamePasswordService usernamePasswordService, TrainingService trainingService) {
        this.userService = userService;
        this.usernamePasswordService = usernamePasswordService;
        this.trainingService = trainingService;
    }

    @Override
    public TraineeEntity mapTraineeCreationRequestDtoToTraineeEntity(TraineeCreationRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeCreationRequestDto must not be null");
        return new TraineeEntity(
            new UserEntity(
                requestDto.getFirstName(),
                requestDto.getLastName(),
                usernamePasswordService.username(requestDto.getFirstName(), requestDto.getLastName()),
                usernamePasswordService.password(),
                true
            ),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }

    @Override
    public TraineeCreationResponseDto mapTraineeEntityToTraineeCreationResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeCreationResponseDto(
            trainee.getUser().getUsername(),
            trainee.getUser().getPassword()
        );
    }

    @Override
    public TraineeRetrievalResponseDto mapTraineeEntityToTraineeRetrievalResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        return new TraineeRetrievalResponseDto(
            trainee.getUser().getFirstName(),
            trainee.getUser().getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            trainee.getUser().getIsActive(),
            trainingService.findAllByTraineeUsernameAndCriteria(trainee.getUser().getUsername(),
                    null, null, null, null)
                .stream()
                .map(TrainingEntity::getTrainer)
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()
                    ),
                    trainerEntity.getSpecialization().getTrainingType()
                )).collect(Collectors.toSet()).stream().toList()
        );
    }

    @Override
    public TraineeUpdateResponseDto mapTraineeEntityToTraineeUpdateResponseDto(TraineeEntity trainee) {
        Assert.notNull(trainee, "TraineeEntity must not be null");
        UserEntity userEntity = trainee.getUser();
        List<TrainerDto> trainerDtoList =
            trainingService.findAllByTraineeUsernameAndCriteria(trainee.getUser().getUsername(),
                    null, null, null, null)
                .stream()
                .map(TrainingEntity::getTrainer)
                .map(trainerEntity -> new TrainerDto(
                    new UserDto(
                        trainerEntity.getUser().getFirstName(),
                        trainerEntity.getUser().getLastName(),
                        trainerEntity.getUser().getUsername(),
                        trainerEntity.getUser().getPassword(),
                        trainerEntity.getUser().getIsActive()
                    ),
                    trainerEntity.getSpecialization().getTrainingType()
                )).collect(Collectors.toSet()).stream().toList();

        return new TraineeUpdateResponseDto(
            userEntity.getUsername(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            trainee.getDateOfBirth(),
            trainee.getAddress(),
            userEntity.getIsActive(),
            trainerDtoList
        );
    }

    @Override
    public TraineeEntity mapTraineeUpdateRequestDtoToTraineeEntity(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new TraineeEntity(
            userService.getByUsername(requestDto.getUsername()),
            requestDto.getDateOfBirth(),
            requestDto.getAddress()
        );
    }

    @Override
    public UserEntity mapTraineeUpdateRequestDtoToUserEntity(TraineeUpdateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeUpdateRequestDto must not be null");
        return new UserEntity(
            requestDto.getFirstName(),
            requestDto.getLastName(),
            requestDto.getUsername(),
            userService.getByUsername(requestDto.getUsername()).getPassword(),
            requestDto.getIsActive()
        );
    }

    @Override
    public UserEntity mapSwitchActivationStateRequestDtoToUserEntity(
        TraineeSwitchActivationStateRequestDto requestDto) {
        Assert.notNull(requestDto, "TraineeSwitchActivationStateRequestDto must not be null");

        UserEntity user = userService.getByUsername(requestDto.getUsername());
        user.setIsActive(!user.getIsActive());
        return user;
    }
}
