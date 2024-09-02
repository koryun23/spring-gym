package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.service.core.DatabasePathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainingFileStorageImpl implements FileStorage<Training> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFileStorageImpl.class);

    private DateConverter dateConverter;
    private DatabasePathService databasePathService;

    private String trainingPath;

    @Override
    public Map<Long, Training> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(trainingPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Long, Training> inMemoryStorage = new HashMap<>();

        while (scanner.hasNextLine()) {
            String currentTrainingString = scanner.nextLine();
            LOGGER.info("Storing the row '{}' to the in-memory storage", currentTrainingString);
            String[] currentTrainingArray = currentTrainingString.split(",");
            Long trainingId = getTrainingIdFromArray(currentTrainingArray);
            Training currentTraining = new Training(
                    trainingId,
                    getTraineeIdFromArray(currentTrainingArray),
                    getTrainerIdFromArray(currentTrainingArray),
                    getTrainingNameFromArray(currentTrainingArray),
                    getTrainingTypeFromArray(currentTrainingArray),
                    getTrainingDateFromArray(currentTrainingArray),
                    getTrainingDurationFromArray(currentTrainingArray)
            );
            LOGGER.info("Converted the row '{}' to {}", currentTrainingString, currentTraining);
            inMemoryStorage.put(trainingId, currentTraining);
            LOGGER.info("Successfully stored {} in the in-memory storage", currentTraining);
        }
        LOGGER.info("In memory storage - {}", inMemoryStorage);
        return inMemoryStorage;
    }

    @Override
    public void persist(Map<Long, Training> inMemoryStorage) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(trainingPath));
            for (Map.Entry<Long, Training> entry : inMemoryStorage.entrySet()) {
                Training currentTraining = entry.getValue();
                LOGGER.info("Persisting {} to the .txt file", currentTraining);
                String stringRepresentationOfTraining = String.format("%d,%d,%d,%s,%s,%s,%d",
                        currentTraining.getTrainingId(),
                        currentTraining.getTraineeId(),
                        currentTraining.getTrainerId(),
                        currentTraining.getName(),
                        currentTraining.getTrainingType(),
                        dateConverter.dateToString(currentTraining.getTrainingDate()),
                        currentTraining.getDuration()
                );

                LOGGER.info("The row being persisted to the .txt file - {}", stringRepresentationOfTraining);
                writer.write(stringRepresentationOfTraining);
                writer.newLine();
                LOGGER.info("Successfully persisted {}, result - {}", currentTraining, stringRepresentationOfTraining);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    public void setDateConverter(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Autowired
    public void setDatabasePathService(DatabasePathService databasePathService) {
        this.databasePathService = databasePathService;
    }

    @PostConstruct
    public void init() {
        trainingPath = databasePathService.getTrainingPath();
    }

    private Long getTrainingIdFromArray(String[] array) {
        return Long.valueOf(array[0]);
    }

    private Long getTraineeIdFromArray(String[] array) {
        return Long.valueOf(array[1]);
    }

    private Long getTrainerIdFromArray(String[] array) {
        return Long.valueOf(array[2]);
    }

    private String getTrainingNameFromArray(String[] array) {
        return array[3];
    }

    private TrainingType getTrainingTypeFromArray(String[] array) {
        return TrainingType.valueOf(array[4]);
    }

    private Date getTrainingDateFromArray(String[] array) {
        return dateConverter.stringToDate(array[5]);
    }

    private Long getTrainingDurationFromArray(String[] array) {
        return Long.valueOf(array[6]);
    }
}
