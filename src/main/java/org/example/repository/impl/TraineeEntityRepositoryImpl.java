package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.core.TraineeEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.id.uuid.StandardRandomStrategy;
import org.hibernate.query.Query;
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
    public Optional<TraineeEntity> findByUsername(String username) {
        List<TraineeEntity> traineeEntityList = findAll();

        for(TraineeEntity traineeEntity : traineeEntityList) {
            if(traineeEntity.getUser().getUsername().equals(username)) {
                return Optional.of(traineeEntity);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteByUsername(String username) {
        TraineeEntity traineeEntity = findByUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.remove(traineeEntity);

        transaction.commit();
        session.close();
    }

    @Override
    public List<TraineeEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TraineeEntity> criteriaQuery = criteriaBuilder.createQuery(TraineeEntity.class);
        Root<TraineeEntity> traineeRoot = criteriaQuery.from(TraineeEntity.class);
        CriteriaQuery<TraineeEntity> select = criteriaQuery.select(traineeRoot);
        Query<TraineeEntity> traineeEntityQuery = session.createQuery(select);

        List<TraineeEntity> traineeEntityList = traineeEntityQuery.list();

        transaction.commit();
        session.close();

        return traineeEntityList;
    }

    @Override
    public Optional<TraineeEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TraineeEntity traineeEntity = session.find(TraineeEntity.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(traineeEntity);
    }

    @Override
    public TraineeEntity save(TraineeEntity trainee) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(trainee);
        transaction.commit();
        session.close();
        return trainee;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(findById(id).orElseThrow(() -> new TraineeNotFoundException(id)));
        transaction.commit();
        session.close();
    }

    @Override
    public TraineeEntity update(TraineeEntity entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TraineeEntity traineeEntityPersisted = session.get(TraineeEntity.class, entity.getId());
        traineeEntityPersisted.setUser(entity.getUser());
        traineeEntityPersisted.setAddress(entity.getAddress());
        traineeEntityPersisted.setDateOfBirth(entity.getDateOfBirth());

        transaction.commit();
        session.close();

        return traineeEntityPersisted;
    }
}
