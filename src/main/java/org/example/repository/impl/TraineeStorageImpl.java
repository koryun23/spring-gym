package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.repository.core.FileStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class TraineeStorageImpl implements FileStorage<Trainee> {

    @Value("$(storage.trainee.path)")
    private String path;

    private Map<Long, Trainee> inMemoryStorage;

    @Override
    public Trainee get(Long id) {
        return null;
    }

    @Override
    public Trainee add(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee remove(Trainee trainee) {
        return null;
    }

    @Override
    public Trainee update(Trainee trainee) {
        return null;
    }

    @Override
    @PostConstruct
    public void parseMemoryFile() {

    }
}
