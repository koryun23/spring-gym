package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class TrainingStorageImpl implements FileStorage<Training> {

    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\training.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerStorageImpl.class);

    private final Map<Long, Training> inMemoryStorage;

    @Autowired
    private DateConverter dateConverter;

    public TrainingStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    public TrainingStorageImpl(Map<Long, Training> map) {
        inMemoryStorage = map;
    }

    @Override
    public Training get(Long id) {
        return inMemoryStorage.get(id);
    }

    @Override
    public Training add(Training training) {
        inMemoryStorage.put(training.getTrainingId(), training);
        persist();
        return training;
    }

    @Override
    public boolean remove(Long id) {
        inMemoryStorage.remove(id);
        persist();
        return true;
    }

    @Override
    public Training update(Training training) {
        inMemoryStorage.put(training.getTrainingId(), training);
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

        while(scanner.hasNextLine()) {
            String currentTrainingString = scanner.nextLine();
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
            LOGGER.info(currentTrainingString.toString());
            inMemoryStorage.put(trainingId, currentTraining);
        }
        LOGGER.info("In memory storage - {}", inMemoryStorage);
    }

    @Override
    public void persist() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
            for(Map.Entry<Long, Training> entry : inMemoryStorage.entrySet()) {
                Training currentTraining = entry.getValue();
                String stringRepresentationOfTraining = String.format("%d,%d,%d,%s,%s,%s,%d",
                        currentTraining.getTrainingId(),
                        currentTraining.getTraineeId(),
                        currentTraining.getTrainerId(),
                        currentTraining.getName(),
                        currentTraining.getTrainingType(),
                        dateConverter.dateToString(currentTraining.getTrainingDate()),
                        currentTraining.getDuration()
                );
                LOGGER.info("Current training - {}", stringRepresentationOfTraining);
                writer.write(stringRepresentationOfTraining);
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
