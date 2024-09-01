package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.SpecializationType;
import org.example.entity.Trainer;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainerFileStorageImpl implements FileStorage<Trainer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerFileStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainer.txt";

    @Override
    public Map<Long, Trainer> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Long, Trainer> inMemoryStorage = new HashMap<>();

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
        return null;
    }

    @Override
    public void persist(Map<Long, Trainer> inMemoryStorage) {
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
