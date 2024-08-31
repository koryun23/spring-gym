package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import org.example.entity.Trainee;
import org.example.exception.TraineeNotFoundException;
import org.example.helper.DateConverter;
import org.example.repository.core.FileStorage;
import org.example.repository.core.TraineeStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.io.*;
import java.util.*;

public class TraineeStorageImpl implements FileStorage<Trainee>, TraineeStorage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeStorageImpl.class);
    private static final String PATH = "C:\\Users\\Koryun\\Desktop\\Koryun\\gym-spring\\src\\main\\java\\org\\example\\repository\\core\\trainee.txt";

    private DateConverter dateConverter;

    private final Map<Long, Trainee> inMemoryStorage; // trainee id - trainee

    public TraineeStorageImpl(Map<Long, Trainee> map) {
        Assert.notNull(map, "In memory storage must not be null");
        inMemoryStorage = map;
    }

    public TraineeStorageImpl() {
        inMemoryStorage = new HashMap<>();
    }

    @Override
    public Trainee get(Long id) {
        LOGGER.info("Retrieving a Trainee with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "Trainee Id must not be null");
        Trainee trainee = inMemoryStorage.get(id);
        if(trainee == null) throw new TraineeNotFoundException(id);
        LOGGER.info("Successfully retrieved a Trainer with an id of {}, result - {}", id, trainee);
        return trainee;
    }

    @Override
    public Trainee add(Trainee trainee) {
        LOGGER.info("Adding {} to the in-memory storage", trainee);
        Assert.notNull(trainee, "Trainee must not be null");
        Trainee addedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully added {} to the in-memory storage", addedTrainee);
        persist();
        return addedTrainee;
    }

    @Override
    public boolean remove(Long id) {
        LOGGER.info("Removing a Trainee with an id of {} from the in-memory storage", id);
        Assert.notNull(id, "Trainee id must not be null");
        if(!inMemoryStorage.containsKey(id)) throw new TraineeNotFoundException(id);
        Trainee removedTrainee = inMemoryStorage.remove(id);
        LOGGER.info("Successfully removed {} from the in-memory storage", removedTrainee);
        persist();
        return true;
    }

    @Override
    public Trainee update(Trainee trainee) {
        LOGGER.info("Updating a Trainee with an id of {}", trainee.getUserId());
        Assert.notNull(trainee, "Trainee must not be null");
        Trainee updatedTrainee = inMemoryStorage.put(trainee.getUserId(), trainee);
        LOGGER.info("Successfully updated a Trainee with an id of {}, final result - {}", trainee.getUserId(), updatedTrainee);
        persist();
        return updatedTrainee;
    }

    @Override
    public Trainee getByUsername(String username) {
        LOGGER.info("Retrieving a Trainee with a username of {}", username);
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");

        for (Map.Entry<Long, Trainee> pair : inMemoryStorage.entrySet()) {
            Trainee trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                LOGGER.info("Successfully retrieved a Trainee with a username of {}, result - {}", username, trainee);
                return trainee;
            }
        }
        LOGGER.error("Failed to retrieve a Trainee with a username of {}, throwing a TraineeNotFoundException", username);
        throw new TraineeNotFoundException(username);
    }

    @Override
    public Optional<Trainee> findByUsername(String username) {
        LOGGER.info("Retrieving an optional of a Trainee with a username of {}", username);
        Assert.notNull(username, "Trainee username must not be null");
        Assert.hasText(username, "Trainee username must not be empty");
        for(Map.Entry<Long, Trainee> pair : inMemoryStorage.entrySet()) {
            Trainee trainee = pair.getValue();
            if(trainee.getUsername().equals(username)) {
                Optional<Trainee> optionalTrainee = Optional.of(trainee);
                LOGGER.info("Successfully retrieved an optional of a Trainee with a username of {}, result - {}", username, optionalTrainee);
                return optionalTrainee;
            }
        }
        Optional<Trainee> optionalTrainee = Optional.empty();
        LOGGER.info("Successfully retrieved an optional of a Trainee with a username of {}, result - {}", username, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        LOGGER.info("Retrieving an optional of a Trainee with an id of {}", id);
        Assert.notNull(id, "Trainee id must not be null");

        Trainee trainee = inMemoryStorage.get(id);
        Optional<Trainee> optionalTrainee = Optional.of(trainee);
        LOGGER.info("Successfully retrieved an optional of a Trainee with an id of {}, result - {}", id, optionalTrainee);
        return optionalTrainee;
    }

    @Override
    public void persist() {
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
    public void parseMemoryFile() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        LOGGER.info("In memory storage - {}", inMemoryStorage.toString());
    }

    @PostConstruct
    public void init() {
        parseMemoryFile();
    }

    @Autowired
    public void setDateConverter(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
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
