package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.example.entity.TraineeEntity;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.service.core.DatabasePathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TraineeFileStorageImpl implements FileStorage<TraineeEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeFileStorageImpl.class);

    private DateConverter dateConverter;
    private DatabasePathService databasePathService;

    private String traineePath;

    @Override
    public void persist(Map<Long, TraineeEntity> inMemoryStorage) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(traineePath));
            for (Map.Entry<Long, TraineeEntity> entry : inMemoryStorage.entrySet()) {
                TraineeEntity currentTrainee = entry.getValue();
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
    public Map<Long, TraineeEntity> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(traineePath));
        } catch (IOException e) {
            LOGGER.error("No file found with path {}", traineePath);
            throw new RuntimeException(e);
        }

        Map<Long, TraineeEntity> inMemoryStorage = new HashMap<>();

        while (scanner.hasNextLine()) {
            String currentTraineeString = scanner.nextLine();
            LOGGER.info("Storing the row '{}' in the in-memory storage", currentTraineeString);
            String[] currentTraineeSplit = currentTraineeString.split(",");
            Long userId = getUserIdFromArray(currentTraineeSplit);
            TraineeEntity currentTrainee = new TraineeEntity(
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

    @Autowired
    @Qualifier("traineeDatabasePathService")
    public void setDatabasePathService(DatabasePathService databasePathService) {
        this.databasePathService = databasePathService;
    }

    /**
     * PostConstruct method.
     */
    @PostConstruct
    public void init() {
        LOGGER.info("Entering the post construct method");
        traineePath = databasePathService.getEntityPath();
        LOGGER.info("trainee path - {}", traineePath);
    }
}
