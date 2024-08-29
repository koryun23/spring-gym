package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainingStorageImpl implements FileStorage<Training> {

    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\training.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private final Map<Long, Training> inMemoryStorage;

    private DateConverter dateConverter;

    public TrainingStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    public TrainingStorageImpl(Map<Long, Training> map) {
        inMemoryStorage = map;
    }

    @Override
    public Training get(Long id) {
        LOGGER.info("Retrieving a Training with an id of {}", id);
        Training training = inMemoryStorage.get(id);
        LOGGER.info("Successfully retrieved a Training with an id of {}, result - {}", id, training);
        return training;
    }

    @Override
    public Training add(Training training) {
        LOGGER.info("Adding a Training with an id of {} to the in-memory storage", training.getTrainingId());
        Training addedTraining = inMemoryStorage.put(training.getTrainingId(), training);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTraining);
        persist();
        return training;
    }

    @Override
    public boolean remove(Long id) {
        LOGGER.info("Removing a Training with an id of {} from the in-memory storage", id);
        Training removedTraining = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTraining);
        persist();
        return true;
    }

    @Override
    public Training update(Training training) {
        LOGGER.info("Updating a Training with an id of {}", training.getTrainingId());
        Training updatedTraining = inMemoryStorage.put(training.getTrainingId(), training);
        LOGGER.info("Successfully updated a Training with an id of {}, result - {}", training.getTrainingId(), updatedTraining);
        persist();
        return training;
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
    }

    @Override
    public void persist() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
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

    @PostConstruct
    public void init() {
        parseMemoryFile();
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
