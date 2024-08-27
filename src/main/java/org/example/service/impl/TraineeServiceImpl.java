package org.example.service.impl;

import org.example.dao.impl.TraineeDao;
import org.example.entity.Trainee;
import org.example.service.core.TraineeService;
import org.example.service.params.TraineeCreateParams;
import org.example.service.params.TraineeUpdateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDao traineeDao;

    @Override
    public Trainee create(TraineeCreateParams params) {
        return traineeDao.save(new Trainee(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getDateOfBirth(),
                params.getAddress()
        ));
    }

    @Override
    public Trainee update(TraineeUpdateParams params) {
        return traineeDao.update(new Trainee(
                params.getUserId(),
                params.getFirstName(),
                params.getLastName(),
                params.getUsername(),
                params.getPassword(),
                params.isActive(),
                params.getDateOfBirth(),
                params.getAddress()
        ));
    }

    @Override
    public boolean delete(Long traineeId) {
        return traineeDao.delete(traineeId);
    }

    @Override
    public Trainee select(Long traineeId) {
        return traineeDao.get(traineeId);
    }
}
