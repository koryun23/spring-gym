package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.example.entity.TraineeEntity;
import org.example.repository.core.TraineeEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeEntityRepositoryImpl implements TraineeEntityRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeEntityRepositoryImpl.class);

    private SessionFactory sessionFactory;

    public TraineeEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Session Factory - {}", sessionFactory);
    }

    @Override
    public Optional<TraineeEntity> findByUsername(String usernaem) {
        return Optional.empty();
    }

    @Override
    public Optional<TraineeEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TraineeEntity save(TraineeEntity trainee) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
