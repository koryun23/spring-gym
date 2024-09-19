package org.example.repository.impl;

import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.example.repository.core.TrainerEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerEntityRepositoryImpl implements TrainerEntityRepository {
    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public List<TrainerEntity> findAll() {
        return List.of();
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TrainerEntity save(TrainerEntity trainer) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
