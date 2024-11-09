package org.example.loader;

import org.example.entity.TrainingType;
import org.example.entity.TrainingTypeEntity;
import org.example.repository.TrainingTypeEntityRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoaderImpl implements DatabaseLoader, CommandLineRunner {

    private final TrainingTypeEntityRepository trainingTypeEntityRepository;

    public DatabaseLoaderImpl(TrainingTypeEntityRepository trainingTypeEntityRepository) {
        this.trainingTypeEntityRepository = trainingTypeEntityRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        saveInitialTrainingTypes();
    }

    @Override
    public void saveInitialTrainingTypes() {
        if (trainingTypeEntityRepository.count() > 0) {
            return;
        }
        for (TrainingType trainingType : TrainingType.values()) {
            trainingTypeEntityRepository.save(new TrainingTypeEntity(trainingType));
        }
    }
}
