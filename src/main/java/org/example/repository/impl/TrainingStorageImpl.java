package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Training;
import org.example.repository.core.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TrainingStorageImpl implements FileStorage<Training> {

    @Value("$(storage.training.path)")
    private String path;

    @Override
    public Training get(Long id) {
        return null;
    }

    @Override
    public Training add(Training training) {
        return null;
    }

    @Override
    public Training remove(Training training) {
        return null;
    }

    @Override
    public Training update(Training training) {
        return null;
    }

    @Override
    @PostConstruct
    public void parseMemoryFile() {

    }

    @Override
    public void persist() {

    }
}
