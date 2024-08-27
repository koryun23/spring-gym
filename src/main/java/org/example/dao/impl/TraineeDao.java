package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Trainee;
import org.example.repository.impl.TraineeStorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraineeDao implements Dao<Trainee> {

    @Autowired
    private TraineeStorageImpl storage;

    @Override
    public Trainee get(Long id) {
        return storage.get(id);
    }

    @Override
    public Trainee save(Trainee trainee) {
        return storage.add(trainee);
    }

    @Override
    public Trainee update(Trainee trainee) {
        return storage.update(trainee);
    }

    @Override
    public boolean delete(Long id) {
        return storage.remove(id);
    }
}
