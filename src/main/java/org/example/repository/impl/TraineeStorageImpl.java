package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class TraineeStorageImpl implements FileStorage<Trainee> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainee.txt";

    @Autowired
    private DateConverter dateConverter;

    private final Map<Long, Trainee> inMemoryStorage; // trainee id - trainee

    public TraineeStorageImpl(Map<Long, Trainee> map) {
        inMemoryStorage = map;
    }

    public TraineeStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainee get(Long id) {
        return inMemoryStorage.get(id);
    }

    @Override
    public Trainee add(Trainee trainee) {
        inMemoryStorage.put(trainee.getUserId(), trainee);
        persist();
        return trainee;
    }

    @Override
    public Trainee remove(Trainee trainee) {
        inMemoryStorage.remove(trainee.getUserId());
        persist();
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        inMemoryStorage.put(trainee.getUserId(), trainee);
        persist();
        return trainee;
    }

    @Override
    public void persist() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(PATH));
            for (Map.Entry<Long, Trainee> entry : inMemoryStorage.entrySet()) {
                Trainee currentTrainee = entry.getValue();
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
                LOGGER.info("Current trainee - {}", stringRepresentationOfTrainee);
                writer.write(stringRepresentationOfTrainee);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine()) {
            String currentTraineeString = scanner.nextLine();
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
            LOGGER.info(currentTrainee.toString());
            inMemoryStorage.put(userId, currentTrainee);
        }
        LOGGER.info("In memory storage - {}", inMemoryStorage.toString());
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

    private Date getDateOfBirthFromArray(String[] array) {
        return dateConverter.stringToDate(array[6]);
    }

    private String getAddressFromArray(String[] array) {
        return array[7];
    }
}
