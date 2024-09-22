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
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.propertyeditors.StringArrayPropertyEditor;
import org.springframework.stereotype.Repository;

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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long trainerEntityId = session.createQuery(trainerIdFromUsernameQuery(), TrainerEntity.class)
            .setParameter("trainerUsername", trainerUsername)
            .uniqueResult().getId(); // single selection, time complexity - log(n), where n is the number of records in trainer table

        Long traineeEntityId = session.createQuery(traineeIdFromUsernameQuery(), TraineeEntity.class)
            .setParameter("traineeUsername", traineeUsername)
            .uniqueResult().getId(); // single selection, time complexity - log(n), where n is the number of records in trainee table

        String query = "SELECT * FROM training WHERE training.trainee_id = :traineeId AND training.trainer_id = :trainerId AND training.date >= :from AND training.date <= :to AND training.training_type_id = :trainingTypeId";

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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long trainerEntityId = session.createQuery(trainerIdFromUsernameQuery(), TrainerEntity.class)
            .setParameter("trainerUsername", trainerUsername)
            .uniqueResult().getId(); // single selection, time complexity - log(n), where n is the number of records in trainer table

        Long traineeEntityId = session.createQuery(traineeIdFromUsernameQuery(), TraineeEntity.class)
            .setParameter("traineeUsername", traineeUsername)
            .uniqueResult().getId(); // single selection, time complexity - log(n), where n is the number of records in trainee table

        String query = "SELECT * FROM training WHERE training.trainee_id = :traineeId AND training.trainer_id = :trainerId AND training.date >= :from AND training.date <= :to";

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

    private String trainerIdFromUsernameQuery() {
        return "select (trainer.id) from trainer JOIN users on users.id = trainer.user_id WHERE users.username = :trainerUsername";
    }

    private String traineeIdFromUsernameQuery() {
        return "select (trainee.id) from trainee JOIN users on users.id = trainee.user_id WHERE users.username = :traineeUsername";
    }
}
