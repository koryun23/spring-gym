package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.SpecializationType;
import org.example.entity.Trainer;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainerStorageImpl implements FileStorage<Trainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainer.txt";

    private final Map<Long, Trainer> inMemoryStorage;

    public TrainerStorageImpl(Map<Long, Trainer> inMemoryStorage) {
        Assert.notNull(inMemoryStorage, "In-memory storage must not be null");
        this.inMemoryStorage = inMemoryStorage;
    }

    public TrainerStorageImpl() {
        this.inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainer get(Long id) {
        LOGGER.info("Retrieving a Trainer with an id of {} from the in-memory storage", id);
        Trainer trainer = inMemoryStorage.get(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainer);
        return trainer;
    }

    @Override
    public Trainer add(Trainer trainer) {
        LOGGER.info("Adding {} to the in-memory storage", trainer);
        Trainer addedTrainer = inMemoryStorage.put(trainer.getUserId(), trainer);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainer);
        persist();
        return trainer;
    }

    @Override
    public boolean remove(Long id) {
        LOGGER.info("Removing a Trainer with an id of {} from the in-memory storage", id);
        Trainer removedTrainer = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainer);
        persist();
        return true;
    }

    @Override
    public Trainer update(Trainer trainer) {
        LOGGER.info("Updating a Trainer with an id of {}", trainer.getUserId());
        Trainer updatedTrainer = inMemoryStorage.put(trainer.getUserId(), trainer);
        LOGGER.info("Successfully updated a Trainer with an id of {}, final result - {}", trainer.getUserId(), updatedTrainer);
        persist();
        return updatedTrainer;
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
            LOGGER.info("Storing the row '{}' in the in-memory storage", currentTrainerString);
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

            LOGGER.info("Converted the row '{}' to {}", currentTrainerString, currentTrainer);
            inMemoryStorage.put(currentTrainer.getUserId(), currentTrainer);
            LOGGER.info("Successfully stored {} in the in-memory storage", currentTrainer);
        }

        LOGGER.info("In memory storage - {}", inMemoryStorage);
    }

    @Override
    public void persist() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
            for (Map.Entry<Long, Trainer> entry : inMemoryStorage.entrySet()) {
                Trainer currentTrainer = entry.getValue();
                LOGGER.info("Persisting {} to the .txt file", currentTrainer);
                String stringRepresentationOfTrainer = String.format("%d,%s,%s,%s,%s,%s,%s",
                        currentTrainer.getUserId(),
                        currentTrainer.getFirstName(),
                        currentTrainer.getLastName(),
                        currentTrainer.getUsername(),
                        currentTrainer.getPassword(),
                        currentTrainer.isActive(),
                        currentTrainer.getSpecialization()
                );
                LOGGER.info("The row being persisted to the .txt file - {}", stringRepresentationOfTrainer);
                writer.write(stringRepresentationOfTrainer);
                writer.newLine();
                LOGGER.info("Successfully persisted {}, result - {}", currentTrainer, stringRepresentationOfTrainer);
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
