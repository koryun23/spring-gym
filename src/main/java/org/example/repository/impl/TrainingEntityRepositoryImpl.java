package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
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
}
