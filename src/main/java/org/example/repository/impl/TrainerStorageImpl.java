package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.SpecializationType;
import org.example.entity.Trainer;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class TrainerStorageImpl implements FileStorage<Trainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainer.txt";

    private final Map<Long, Trainer> inMemoryStorage;

    public TrainerStorageImpl(Map<Long, Trainer> inMemoryStorage) {
        this.inMemoryStorage = inMemoryStorage;
    }

    public TrainerStorageImpl() {
        this.inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainer get(Long id) {
        return inMemoryStorage.get(id);
    }

    @Override
    public Trainer add(Trainer trainer) {
        inMemoryStorage.put(trainer.getUserId(), trainer);
        persist();
        return trainer;
    }

    @Override
    public boolean remove(Long id) {
        inMemoryStorage.remove(id);
        persist();
        return true;
    }

    @Override
    public Trainer update(Trainer trainer) {
        inMemoryStorage.put(trainer.getUserId(), trainer);
        persist();
        return trainer;
    }

    @Override
    public void parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine()) {
            String currentTrainerString = scanner.nextLine();
            String[] currentTrainerArray = currentTrainerString.split(",");
            Trainer currentTrainer = new Trainer(
                    getUserIdFromArray(currentTrainerArray),
                    getFirstNameFromArray(currentTrainerArray),
                    getLastNameFromArray(currentTrainerArray),
                    getUsernameFromArray(currentTrainerArray),
                    getPasswordFromArray(currentTrainerArray),
                    getIsActiveFromArray(currentTrainerArray),
                    getSpecializationFromArray(currentTrainerArray)
            );

            LOGGER.info(currentTrainer.toString());
            inMemoryStorage.put(currentTrainer.getUserId(), currentTrainer);
        }

        LOGGER.info("In memoty storage - {}", inMemoryStorage);
    }

    @Override
    public void persist() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
            for (Map.Entry<Long, Trainer> entry : inMemoryStorage.entrySet()) {
                Trainer currentTrainer = entry.getValue();
                String stringRepresentationOfTrainer = String.format("%d,%s,%s,%s,%s,%s,%s",
                        currentTrainer.getUserId(),
                        currentTrainer.getFirstName(),
                        currentTrainer.getLastName(),
                        currentTrainer.getUsername(),
                        currentTrainer.getPassword(),
                        currentTrainer.isActive(),
                        currentTrainer.getSpecialization()
                );
                LOGGER.info("Current trainer - {}", stringRepresentationOfTrainer);
                writer.write(stringRepresentationOfTrainer);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostConstruct
    public void init() {
        parseMemoryFile();
    }

    private Long getUserIdFromArray(String[] array) {
        return Long.valueOf(array[0]);
    }

    private String getFirstNameFromArray(String[] array) {
        return array[1];
    }

    private String getLastNameFromArray(String[] array) {
        return array[2];
    }

    private String getUsernameFromArray(String[] array) {
        return array[3];
    }

    private String getPasswordFromArray(String[] array) {
        return array[4];
    }

    private boolean getIsActiveFromArray(String[] array) {
        return Boolean.parseBoolean(array[5]);
    }

    private SpecializationType getSpecializationFromArray(String[] array) {
        return SpecializationType.valueOf(array[6]);
    }
}
