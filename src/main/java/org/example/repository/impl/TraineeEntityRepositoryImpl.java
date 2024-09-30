package org.example.repository.impl;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TraineeNotFoundException;
import org.example.repository.core.TraineeEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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


    //TODO as far as I understand do not have gargantuan that transaction 100% want rollback or commit.
    @Override
    public Optional<TraineeEntity> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TraineeEntity traineeEntity =
            session.createQuery(
                    "select t from TraineeEntity t join UserEntity u on t.user.id = u.id where u.username = :username",
                    TraineeEntity.class)
                .setParameter("username", username)
                .uniqueResult();

        transaction.commit();
        session.close();

        return Optional.ofNullable(traineeEntity);
    }

    @Override
    public void deleteByUsername(String username) {
        TraineeEntity traineeEntity =
            findByUsername(username).orElseThrow(() -> new TraineeNotFoundException(username));

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (traineeEntity.getTrainingEntityList() != null) {
            for (TrainingEntity training : traineeEntity.getTrainingEntityList()) {
                session.remove(training);
            }
        }

        session.remove(traineeEntity);
        session.remove(traineeEntity.getUser());

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
        TraineeEntity traineeEntity = findById(id).orElseThrow(() -> new TraineeNotFoundException(id));

        System.out.println(traineeEntity);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        if (traineeEntity.getTrainingEntityList() != null) {
            for (TrainingEntity training : traineeEntity.getTrainingEntityList()) {
                session.remove(training);
            }
        }
        session.remove(traineeEntity);
        session.remove(traineeEntity.getUser());

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
