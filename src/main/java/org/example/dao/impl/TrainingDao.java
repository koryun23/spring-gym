package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Training;
import org.example.repository.impl.TrainingStorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingDao implements Dao<Training> {

    @Autowired
    private TrainingStorageImpl storage;

    @Override
    public Training get(Long id) {
        return storage.get(id);
    }

    @Override
    public Training save(Training training) {
        return storage.add(training);
    }

    @Override
    public Training update(Training training) {
        return storage.update(training);
    }

    @Override
    public boolean delete(Long id) {
        return storage.remove(id);
    }
}
