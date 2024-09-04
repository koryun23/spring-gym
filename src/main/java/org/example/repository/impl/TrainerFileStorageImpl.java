package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.SpecializationType;
import org.example.entity.TrainerEntity;
import org.example.repository.core.FileStorage;
import org.example.service.core.DatabasePathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainerFileStorageImpl implements FileStorage<TrainerEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFileStorageImpl.class);

    private DatabasePathService databasePathService;

    private String trainerPath;

    @Override
    public Map<Long, TrainerEntity> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(trainerPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Long, TrainerEntity> inMemoryStorage = new HashMap<>();

        while (scanner.hasNextLine()) {
            String currentTrainerString = scanner.nextLine();
            LOGGER.info("Storing the row '{}' in the in-memory storage", currentTrainerString);
            String[] currentTrainerArray = currentTrainerString.split(",");
            TrainerEntity currentTrainerEntity = new TrainerEntity(
                    getUserIdFromArray(currentTrainerArray),
                    getFirstNameFromArray(currentTrainerArray),
                    getLastNameFromArray(currentTrainerArray),
                    getUsernameFromArray(currentTrainerArray),
                    getPasswordFromArray(currentTrainerArray),
                    getIsActiveFromArray(currentTrainerArray),
                    getSpecializationFromArray(currentTrainerArray)
            );

            LOGGER.info("Converted the row '{}' to {}", currentTrainerString, currentTrainerEntity);
            inMemoryStorage.put(currentTrainerEntity.getUserId(), currentTrainerEntity);
            LOGGER.info("Successfully stored {} in the in-memory storage", currentTrainerEntity);
        }

        LOGGER.info("In memory storage - {}", inMemoryStorage);
        return inMemoryStorage;
    }

    @Override
    public void persist(Map<Long, TrainerEntity> inMemoryStorage) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(trainerPath));
            for (Map.Entry<Long, TrainerEntity> entry : inMemoryStorage.entrySet()) {
                TrainerEntity currentTrainerEntity = entry.getValue();
                LOGGER.info("Persisting {} to the .txt file", currentTrainerEntity);
                String stringRepresentationOfTrainer = String.format("%d,%s,%s,%s,%s,%s,%s",
                        currentTrainerEntity.getUserId(),
                        currentTrainerEntity.getFirstName(),
                        currentTrainerEntity.getLastName(),
                        currentTrainerEntity.getUsername(),
                        currentTrainerEntity.getPassword(),
                        currentTrainerEntity.isActive(),
                        currentTrainerEntity.getSpecialization()
                );
                LOGGER.info("The row being persisted to the .txt file - {}", stringRepresentationOfTrainer);
                writer.write(stringRepresentationOfTrainer);
                writer.newLine();
                LOGGER.info("Successfully persisted {}, result - {}", currentTrainerEntity, stringRepresentationOfTrainer);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Autowired
    public void setDatabasePathService(DatabasePathService databasePathService) {
        this.databasePathService = databasePathService;
    }

    @PostConstruct
    public void init() {
        trainerPath = databasePathService.getTrainerPath();
    }
}
