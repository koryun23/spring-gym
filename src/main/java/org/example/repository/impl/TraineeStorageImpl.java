package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.repository.core.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

    @Value("classpath:trainee.txt")
    private Resource resource;

    private Map<Long, Trainee> inMemoryStorage; // trainee id - trainee

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
        return trainee;
    }

    @Override
    public Trainee remove(Trainee trainee) {
        inMemoryStorage.remove(trainee.getUserId());
        return trainee;
    }

    @Override
    public Trainee update(Trainee trainee) {
        inMemoryStorage.put(trainee.getUserId(), trainee);
        return trainee;
    }

    @Override
    @PostConstruct
    public void parseMemoryFile() {
        Scanner scanner;
        File file = null;
        try {
            file = resource.getFile();
            scanner = new Scanner(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(scanner.hasNextLine()) {
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
        DateFormat df = new SimpleDateFormat("yyyy-dd-mm");
        try {
            return df.parse(array[6]);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAddressFromArray(String[] array) {
        return array[7];
    }
}
