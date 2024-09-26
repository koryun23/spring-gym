package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.example.entity.TraineeEntity;
import org.example.entity.TrainerEntity;
import org.example.entity.TrainingEntity;
import org.example.exception.TrainingNotFoundException;
import org.example.repository.core.TrainingEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public class TrainingEntityRepositoryImpl implements TrainingEntityRepository {

    private SessionFactory sessionFactory;

    public TrainingEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TrainingEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainingEntity> criteriaQuery = criteriaBuilder.createQuery(TrainingEntity.class);
        Root<TrainingEntity> rootTraining = criteriaQuery.from(TrainingEntity.class);
        CriteriaQuery<TrainingEntity> select = criteriaQuery.select(rootTraining);
        Query<TrainingEntity> query = session.createQuery(select);

        List<TrainingEntity> trainingEntityList = query.list();

        transaction.commit();
        session.close();

        return trainingEntityList;
    }

    @Override
    public Optional<TrainingEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrainingEntity trainingEntity = session.find(TrainingEntity.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(trainingEntity);
    }

    @Override
    public TrainingEntity save(TrainingEntity training) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(training);
        transaction.commit();
        session.close();
        return training;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(findById(id).orElseThrow(() -> new TrainingNotFoundException(id)));
        transaction.commit();
        session.close();
    }

    @Override
    public TrainingEntity update(TrainingEntity entity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TrainingEntity updatedTrainingEntity = session.merge(entity);

        transaction.commit();
        session.close();

        return updatedTrainingEntity;
    }

    @Override
    public List<TrainingEntity> findAllByTraineeUsernameAndCriteria(String traineeUsername, Date from, Date to,
                                                                    String trainerUsername, Long trainingTypeId) {

        Assert.notNull(traineeUsername, "Trainee Username must not be null");

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        // single selection, time complexity - log(n), where n is the number of records in trainer table
        Long trainerEntityId = session.createQuery(trainerIdFromUsernameQuery(), Long.class)
            .setParameter("trainerUsername", trainerUsername)
            .uniqueResult();

        // single selection, time complexity - log(n), where n is the number of records in trainee table
        Long traineeEntityId = session.createQuery(traineeIdFromUsernameQuery(), Long.class)
            .setParameter("traineeUsername", traineeUsername)
            .uniqueResult();

        String query = String.format("select t from TrainingEntity t WHERE t.trainee.id = :traineeId "
                + "%s t.trainer.id = :trainerId "
                + "%s t.date >= :from "
                + "%s t.date <= :to "
                + "%s t.trainingType.id = :trainingTypeId",
            trainerUsername == null ? "OR" : "AND",
            from == null ? "OR" : "AND",
            to == null ? "OR" : "AND",
            trainingTypeId == null ? "OR" : "AND");

        List<TrainingEntity> trainingEntityList = session.createQuery(query, TrainingEntity.class)
            .setParameter("traineeId", traineeEntityId)
            .setParameter("trainerId", trainerEntityId)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("trainingTypeId", trainingTypeId)
            .list();

        transaction.commit();
        session.close();

        return trainingEntityList;
    }

    @Override
    public List<TrainingEntity> findAllByTrainerUsernameAndCriteria(String trainerUsername, Date from, Date to,
                                                                    String traineeUsername) {
        Assert.notNull(trainerUsername, "Trainer Username must not be null");

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long trainerEntityId = session.createQuery(trainerIdFromUsernameQuery(), Long.class)
            .setParameter("trainerUsername", trainerUsername)
            .uniqueResult(); // single selection, time complexity - log(n), where n is the number of records in trainer table

        Long traineeEntityId = session.createQuery(traineeIdFromUsernameQuery(), Long.class)
            .setParameter("traineeUsername", traineeUsername)
            .uniqueResult(); // single selection, time complexity - log(n), where n is the number of records in trainee table

        String query = String.format("select t from TrainingEntity t WHERE t.trainer.id = :trainerId "
                + "%s t.trainee.id = :traineeId "
                + "%s t.date >= :from "
                + "%s t.date <= :to",
            traineeUsername == null ? "OR" : "AND",
            from == null ? "OR" : "AND",
            to == null ? "OR" : "AND");

        List<TrainingEntity> trainingEntityList = session.createQuery(query, TrainingEntity.class)
            .setParameter("traineeId", traineeEntityId)
            .setParameter("trainerId", trainerEntityId)
            .setParameter("from", from)
            .setParameter("to", to)
            .list();

        transaction.commit();
        session.close();

        return trainingEntityList;
    }

    @Override
    public void deleteAllByTraineeUsername(String traineeUsername) {
        Assert.notNull(traineeUsername, "Trainee Username must not be null");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long traineeId = session.createQuery(traineeIdFromUsernameQuery(), Long.class)
            .setParameter("traineeUsername", traineeUsername)
            .uniqueResult();

        List<TrainingEntity> allTrainings =
            findAllByTraineeUsernameAndCriteria(traineeUsername, null, null, null, null);
        for (TrainingEntity training : allTrainings) {
            session.remove(training);
        }

        transaction.commit();
        session.close();
    }

    @Override
    public void deleteAllByTrainerUsername(String trainerUsername) {
        Assert.notNull(trainerUsername, "Trainer Username must not be null");
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<TrainingEntity> allTrainings =
            findAllByTrainerUsernameAndCriteria(trainerUsername, null, null, null);
        for (TrainingEntity training : allTrainings) {
            session.remove(training);
        }
        transaction.commit();
        session.close();
    }

    private String trainerIdFromUsernameQuery() {
        return "select (t.id) from TrainerEntity t JOIN UserEntity u on u.id = t.user.id "
            + "WHERE u.username = :trainerUsername";
    }

    private String traineeIdFromUsernameQuery() {
        return "select (t.id) from TraineeEntity t JOIN UserEntity u on u.id = t.user.id "
            + "WHERE u.username = :traineeUsername";
    }
}
