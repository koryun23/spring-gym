package org.example.repository.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.repository.core.UserEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserEntityRepositoryImpl implements UserEntityRepository {

    private final SessionFactory sessionFactory;

    public UserEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserEntity userEntity =
            session.createQuery("select u from users where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .uniqueResult();

        transaction.commit();
        session.close();

        return Optional.ofNullable(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserEntity userEntityPersisted = session.get(UserEntity.class, userEntity.getId());
        userEntityPersisted.setFirstName(userEntity.getFirstName());
        userEntityPersisted.setLastName(userEntity.getLastName());
        userEntityPersisted.setUsername(userEntity.getUsername());
        userEntityPersisted.setPassword(userEntity.getPassword());
        userEntityPersisted.setIsActive(userEntity.getIsActive());

        transaction.commit();
        session.close();

        return userEntityPersisted;
    }

    @Override
    public List<UserEntity> findAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteriaQuery = criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userRoot = criteriaQuery.from(UserEntity.class);
        CriteriaQuery<UserEntity> select = criteriaQuery.select(userRoot);
        Query<UserEntity> userEntityQuery = session.createQuery(select);

        List<UserEntity> userEntityList = userEntityQuery.list();

        transaction.commit();
        session.close();

        return userEntityList;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserEntity userEntity = session.find(UserEntity.class, id);
        transaction.commit();
        session.close();
        return Optional.ofNullable(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.remove(findById(id).orElseThrow(() -> new UserNotFoundException(id)));
        transaction.commit();
        session.close();
    }

    @Override
    public UserEntity save(UserEntity user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        transaction.commit();
        session.close();
        return user;
    }
}
