package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.example.entity.TrainingEntity;
import org.example.entity.TrainingType;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.service.core.DatabasePathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TrainingFileStorageImpl implements FileStorage<TrainingEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingFileStorageImpl.class);

    private DateConverter dateConverter;
    private DatabasePathService databasePathService;

    private String trainingPath;

    @Override
    public Map<Long, TrainingEntity> parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(trainingPath));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Map<Long, TrainingEntity> inMemoryStorage = new HashMap<>();

        while (scanner.hasNextLine()) {
            String currentTrainingString = scanner.nextLine();
            LOGGER.info("Storing the row '{}' to the in-memory storage", currentTrainingString);
            String[] currentTrainingArray = currentTrainingString.split(",");
            Long trainingId = getTrainingIdFromArray(currentTrainingArray);
            TrainingEntity currentTrainingEntity = new TrainingEntity(
                trainingId,
                getTraineeIdFromArray(currentTrainingArray),
                getTrainerIdFromArray(currentTrainingArray),
                getTrainingNameFromArray(currentTrainingArray),
                getTrainingTypeFromArray(currentTrainingArray),
                getTrainingDateFromArray(currentTrainingArray),
                getTrainingDurationFromArray(currentTrainingArray)
            );
            LOGGER.info("Converted the row '{}' to {}", currentTrainingString, currentTrainingEntity);
            inMemoryStorage.put(trainingId, currentTrainingEntity);
            LOGGER.info("Successfully stored {} in the in-memory storage", currentTrainingEntity);
        }
        LOGGER.info("In memory storage - {}", inMemoryStorage);
        return inMemoryStorage;
    }

    @Override
    public void persist(Map<Long, TrainingEntity> inMemoryStorage) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(trainingPath));
            for (Map.Entry<Long, TrainingEntity> entry : inMemoryStorage.entrySet()) {
                TrainingEntity currentTrainingEntity = entry.getValue();
                LOGGER.info("Persisting {} to the .txt file", currentTrainingEntity);
                String stringRepresentationOfTraining = String.format("%d,%d,%d,%s,%s,%s,%d",
                    currentTrainingEntity.getTrainingId(),
                    currentTrainingEntity.getTraineeId(),
                    currentTrainingEntity.getTrainerId(),
                    currentTrainingEntity.getName(),
                    currentTrainingEntity.getTrainingType(),
                    dateConverter.dateToString(currentTrainingEntity.getTrainingDate()),
                    currentTrainingEntity.getDuration()
                );

                LOGGER.info("The row being persisted to the .txt file - {}", stringRepresentationOfTraining);
                writer.write(stringRepresentationOfTraining);
                writer.newLine();
                LOGGER.info("Successfully persisted {}, result - {}", currentTrainingEntity,
                    stringRepresentationOfTraining);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    @Autowired
    public void setDateConverter(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    @Autowired
    @Qualifier("trainingDatabasePathService")
    public void setDatabasePathService(DatabasePathService databasePathService) {
        this.databasePathService = databasePathService;
    }

    @PostConstruct
    public void init() {
        trainingPath = databasePathService.getEntityPath();
    }
}
