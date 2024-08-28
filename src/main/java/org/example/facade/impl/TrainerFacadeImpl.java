package org.example.facade.impl;

import org.example.dto.request.TrainerCreationRequestDto;
import org.example.dto.request.TrainerUpdateRequestDto;
import org.example.dto.response.TrainerCreationResponseDto;
import org.example.dto.response.TrainerRetrievalResponseDto;
import org.example.dto.response.TrainerUpdateResponseDto;
import org.example.entity.Trainer;
import org.example.facade.core.TrainerFacade;
import org.example.service.core.TrainerService;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerFacadeImpl implements TrainerFacade {

    @Autowired
    private TrainerService trainerService;

    @Override
    public TrainerCreationResponseDto createTrainer(TrainerCreationRequestDto requestDto) {
        Trainer trainer = trainerService.create(new TrainerCreateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));
        return new TrainerCreationResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );
    }

    @Override
    public TrainerUpdateResponseDto updateTrainer(TrainerUpdateRequestDto requestDto) {
        Trainer trainer = trainerService.update(new TrainerUpdateParams(
                requestDto.getUserId(),
                requestDto.getFirstName(),
                requestDto.getLastName(),
                requestDto.getUsername(),
                requestDto.getPassword(),
                requestDto.isActive(),
                requestDto.getSpecializationType()
        ));

        return new TrainerUpdateResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );
    }

    @Override
    public TrainerRetrievalResponseDto retrieveTrainer(Long trainerId) {
        Trainer trainer = trainerService.select(trainerId);
        return new TrainerRetrievalResponseDto(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive(),
                trainer.getSpecialization()
        );
    }
}
