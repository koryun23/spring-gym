package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainer;
import org.example.repository.core.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrainerStorageImpl implements FileStorage<Trainer> {

    @Value("$(storage.trainer.path)")
    private String path;

    @Override
    public Trainer get(Long id) {
        return null;
    }

    @Override
    public Trainer add(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer remove(Trainer trainer) {
        return null;
    }

    @Override
    public Trainer update(Trainer trainer) {
        return null;
    }

    @Override
    @PostConstruct
    public void parseMemoryFile() {

    }
}