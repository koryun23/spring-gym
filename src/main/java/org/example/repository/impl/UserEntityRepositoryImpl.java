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

    /**
     * Constructor.
     */
    public UserEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        UserEntity userEntity =
            session.createQuery("select u from UserEntity u where u.username = :username", UserEntity.class)
                .setParameter("username", username)
                .uniqueResult();

        transaction.commit();
        session.close();

        return Optional.ofNullable(userEntity);
    }

    @Override
    public Optional<UserEntity> findByPassword(String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Optional<UserEntity> optionalUser =
            session.createQuery("select u from UserEntity u where u.password = :password", UserEntity.class)
                .setParameter("password", password)
                .uniqueResultOptional();

        transaction.commit();
        session.close();

        return optionalUser;
    }

    @Override
    public List<UserEntity> findAllByUsernameContains(String pattern) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        List<UserEntity> usernames =
            session.createQuery("select u from UserEntity u where u.username like '" + pattern + "%'",
                    UserEntity.class)
                .getResultList();

        transaction.commit();
        session.close();
        return usernames;

    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Long userId = session.createQuery("select u.id from UserEntity u where u.username = :username", Long.class)
            .setParameter("username", userEntity.getUsername())
            .uniqueResult();

        userEntity.setId(userId);
        session.merge(userEntity);

        transaction.commit();
        session.close();

        return userEntity;
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
        session.persist(user);
        transaction.commit();
        session.close();
        return user;
    }
}
