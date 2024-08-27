package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Trainer;
import org.example.repository.impl.TrainerStorageImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerDao implements Dao<Trainer> {

    @Autowired
    private TrainerStorageImpl storage;

    @Override
    public Trainer get(Long id) {
        return storage.get(id);
    }

    @Override
    public Trainer save(Trainer trainer) {
        return storage.add(trainer);
    }

    @Override
    public Trainer update(Trainer trainer) {
        return storage.update(trainer);
    }

    @Override
    public boolean delete(Long id) {
        return storage.remove(id);
    }
}
