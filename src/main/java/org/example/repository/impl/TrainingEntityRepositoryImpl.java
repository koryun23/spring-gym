package org.example.repository.impl;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingEntity;
import org.example.repository.core.TrainingEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingEntityRepositoryImpl implements TrainingEntityRepository {
    @Override
    public List<TrainingEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TrainingEntity save(TrainingEntity training) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
