package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.example.entity.TrainerEntity;
import org.example.exception.TrainerNotFoundException;
import org.example.repository.core.TrainerEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class TrainerEntityRepositoryImpl implements TrainerEntityRepository {

    private SessionFactory sessionFactory;

    public TrainerEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<TrainerEntity> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TrainerEntity trainerEntity = session.createQuery(
                "select t from TrainerEntity t join UserEntity u on t.user.id = u.id where u.username = :username",
                TrainerEntity.class)
            .setParameter("username", username)
            .uniqueResult();

        transaction.commit();
        session.close();
        return Optional.ofNullable(trainerEntity);
    }

    @Override
    public List<TrainerEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainerEntity> criteriaQuery = criteriaBuilder.createQuery(TrainerEntity.class);
        Root<TrainerEntity> rootTrainer = criteriaQuery.from(TrainerEntity.class);
        CriteriaQuery<TrainerEntity> select = criteriaQuery.select(rootTrainer);
        Query<TrainerEntity> query = session.createQuery(select);

        List<TrainerEntity> trainerEntityList = query.list();

        transaction.commit();
        session.close();

        return trainerEntityList;
    }

    @Override
    public Optional<TrainerEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TrainerEntity trainerEntity = session.find(TrainerEntity.class, id);

        transaction.commit();
        session.close();
        return Optional.ofNullable(trainerEntity);
    }

    @Override
    public TrainerEntity save(TrainerEntity trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(trainer);

        transaction.commit();
        session.close();

        return trainer;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(findById(id).orElseThrow(() -> new TrainerNotFoundException(id)));
        transaction.commit();
        session.close();
    }

    @Override
    public TrainerEntity update(TrainerEntity trainer) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        TrainerEntity updatedTrainer = session.merge(trainer);
        transaction.commit();
        session.close();
        return updatedTrainer;
    }

    @Override
    public List<TrainerEntity> findAllTrainersNotAssignedTo(String traineeUsername) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

//        String assignedTrainersQuery =
//            "select training.trainer.id from TrainingEntity training JOIN TraineeEntity trainee "
//                + "ON trainee.id = training.trainee.id JOIN UserEntity u ON u.id = trainee.user.id "
//                + "where u.username = :username";
        String assignedTrainersQuery =
            "select trainer.id from Trainee trainee join t.trainerEntityList trainer where trainee.username = :username";

        List<Long> assignedTrainerIdList = session.createQuery(assignedTrainersQuery, Long.class)
            .setParameter("username", traineeUsername)
            .list();

        String nonAssignedTrainersQuery = "select t from TrainerEntity t where t.id not in (:assignedTrainers)";

        List<TrainerEntity> nonAssignedTrainerIdList =
            session.createQuery(nonAssignedTrainersQuery, TrainerEntity.class)
                .setParameter("assignedTrainers", assignedTrainerIdList)
                .list();

        transaction.commit();
        session.close();
        return nonAssignedTrainerIdList;
    }
}
