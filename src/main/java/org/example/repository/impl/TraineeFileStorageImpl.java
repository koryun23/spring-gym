package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TraineeFileStorageImpl implements FileStorage<Trainee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFileStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainee.txt";

    private DateConverter dateConverter;

    @Override
    public void persist(Map<Long, Trainee> inMemoryStorage) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
            for (Map.Entry<Long, Trainee> entry : inMemoryStorage.entrySet()) {
                Trainee currentTrainee = entry.getValue();
                LOGGER.info("Persisting {} to the .txt file", currentTrainee);
                String stringRepresentationOfTrainee = String.format("%d,%s,%s,%s,%s,%s,%s,%s",
                        currentTrainee.getUserId(),
                        currentTrainee.getFirstName(),
                        currentTrainee.getLastName(),
                        currentTrainee.getUsername(),
                        currentTrainee.getPassword(),
                        currentTrainee.isActive(),
                        dateConverter.dateToString(currentTrainee.getDateOfBirth()),
                        currentTrainee.getAddress()
                );
                LOGGER.info("The row being persisted to the .txt file - {}", stringRepresentationOfTrainee);
                writer.write(stringRepresentationOfTrainee);
                writer.newLine();
                LOGGER.info("Successfully persisted {}, result - {}", currentTrainee, stringRepresentationOfTrainee);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Long, Trainee> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<Long, Trainee> inMemoryStorage = new HashMap<>();

        while (scanner.hasNextLine()) {
            String currentTraineeString = scanner.nextLine();
            LOGGER.info("Storing the row '{}' in the in-memory storage", currentTraineeString);
            String[] currentTraineeSplit = currentTraineeString.split(",");
            Long userId = getUserIdFromArray(currentTraineeSplit);
            Trainee currentTrainee = new Trainee(
                    userId,
                    getFirstNameFromArray(currentTraineeSplit),
                    getLastNameFromArray(currentTraineeSplit),
                    getUsernameFromArray(currentTraineeSplit),
                    getPasswordFromArray(currentTraineeSplit),
                    getIsActiveFromArray(currentTraineeSplit),
                    getDateOfBirthFromArray(currentTraineeSplit),
                    getAddressFromArray(currentTraineeSplit)
            );
            LOGGER.info("Converted the row '{}' to {}", currentTraineeString, currentTrainee);
            inMemoryStorage.put(userId, currentTrainee);
            LOGGER.info("Successfully stored {} in the in-memory storage", currentTrainee);
        }
        LOGGER.info("In memory storage - {}", inMemoryStorage);
        return inMemoryStorage;
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

    private Date getDateOfBirthFromArray(String[] array) {
        return dateConverter.stringToDate(array[6]);
    }

    private String getAddressFromArray(String[] array) {
        return array[7];
    }

    @Autowired
    public void setDateConverter(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }
}
