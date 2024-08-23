package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Training;
import org.example.repository.core.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainingDao implements Dao<Training> {

    @Autowired
    private Storage storage;

    @Override
    public Training get(Long id) {
        return null;
    }

    @Override
    public Training save(Training training) {
        return null;
    }

    @Override
    public Training update(Training training) {
        return null;
    }

    @Override
    public Training delete(Training training) {
        return null;
    }
}
