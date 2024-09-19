package org.example.repository.impl;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingTypeEntity;
import org.example.repository.core.TrainingTypeEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypeEntityRepositoryImpl implements TrainingTypeEntityRepository {
    @Override
    public List<TrainingTypeEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<TrainingTypeEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TrainingTypeEntity save(TrainingTypeEntity trainingTypeEntity) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
