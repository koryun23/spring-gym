package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import org.example.entity.TrainingTypeEntity;
import org.example.repository.core.TrainingTypeEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingTypeEntityRepositoryImpl implements TrainingTypeEntityRepository {

    private SessionFactory sessionFactory;

    public TrainingTypeEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TrainingTypeEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<TrainingTypeEntity> criteriaQuery = criteriaBuilder.createQuery(TrainingTypeEntity.class);
        Root<TrainingTypeEntity> rootTrainingType = criteriaQuery.from(TrainingTypeEntity.class);
        CriteriaQuery<TrainingTypeEntity> select = criteriaQuery.select(rootTrainingType);
        Query<TrainingTypeEntity> query = session.createQuery(select);

        List<TrainingTypeEntity> trainingTypeEntityList = query.list();

        transaction.commit();
        session.close();

        return trainingTypeEntityList;
    }

    @Override
    public Optional<TrainingTypeEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        TrainingTypeEntity trainingTypeEntity = session.find(TrainingTypeEntity.class, id);

        transaction.commit();
        session.close();

        return Optional.ofNullable(trainingTypeEntity);
    }

    @Override
    public TrainingTypeEntity save(TrainingTypeEntity trainingTypeEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.persist(transaction);

        transaction.commit();
        session.close();

        return trainingTypeEntity;
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.remove(findById(id));

        transaction.commit();
        session.close();
    }
}
