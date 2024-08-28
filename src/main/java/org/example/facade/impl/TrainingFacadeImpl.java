package org.example.facade.impl;

import org.example.dto.request.TrainingCreationRequestDto;
import org.example.dto.response.TrainingCreationResponseDto;
import org.example.dto.response.TrainingRetrievalResponseDto;
import org.example.entity.Training;
import org.example.facade.core.TrainingFacade;
import org.example.service.core.TrainingService;
import org.example.service.params.TrainingCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingFacadeImpl implements TrainingFacade {

    @Autowired
    private TrainingService trainingService;

    @Override
    public TrainingCreationResponseDto createTraining(TrainingCreationRequestDto requestDto) {
        Training training = trainingService.create(new TrainingCreateParams(
                requestDto.getTrainingId(),
                requestDto.getTraineeId(),
                requestDto.getTrainerId(),
                requestDto.getName(),
                requestDto.getTrainingType(),
                requestDto.getTrainingDate(),
                requestDto.getDuration()
        ));
        return new TrainingCreationResponseDto(
                training.getTrainingId(),
                training.getTraineeId(),
                training.getTrainerId(),
                training.getName(),
                training.getTrainingType(),
                training.getTrainingDate(),
                training.getDuration()
        );
    }

    @Override
    public TrainingRetrievalResponseDto retrieveTraining(Long trainingId) {
        Training training = trainingService.select(trainingId);
        return new TrainingRetrievalResponseDto(
                training.getTrainingId(),
                training.getTraineeId(),
                training.getTrainerId(),
                training.getName(),
                training.getTrainingType(),
                training.getTrainingDate(),
                training.getDuration()
        );
    }
}
