package org.example.service.impl;

import org.example.dao.impl.TrainerDao;
import org.example.entity.Trainer;
import org.example.service.core.TrainerService;
import org.example.service.params.TrainerCreateParams;
import org.example.service.params.TrainerUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDao trainerDao;

    @Override
    public Trainer create(TrainerCreateParams params) {
        return trainerDao.save(new Trainer(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getSpecializationType()
        ));
    }

    @Override
    public Trainer update(TrainerUpdateParams params) {
        return trainerDao.update(new Trainer(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getSpecializationType()
        ));
    }

    @Override
    public Trainer select(Long trainerId) {
        return trainerDao.get(trainerId);
    }
}
