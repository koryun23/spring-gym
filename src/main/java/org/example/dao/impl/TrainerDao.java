package org.example.dao.impl;

import org.example.dao.core.Dao;
import org.example.entity.Trainer;
import org.example.repository.core.Storage;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerDao implements Dao<Trainer> {

    @Autowired
    private Storage storage;

    @Override
    public Trainer get(Long id) {
        return null;
    }

    @Override
    public Trainer save(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer delete(Trainer trainer) {
        return null;
    }
}
