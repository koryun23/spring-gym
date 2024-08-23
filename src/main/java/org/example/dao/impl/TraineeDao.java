package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Trainee;
import org.example.repository.core.Storage;
import org.springframework.beans.factory.annotation.Autowired;


public class TraineeDao implements Dao<Trainee> {

    @Autowired
    private Storage storage;

    @Override
    public Trainee get(Long id) {
        return null;
    }

    @Override
    public Trainee save(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee delete(Trainee trainee) {
        return null;
    }
}
