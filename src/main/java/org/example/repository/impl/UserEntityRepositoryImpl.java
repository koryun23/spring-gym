package org.example.repository.impl;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.UserEntity;
import org.example.exception.UserNotFoundException;
import org.example.repository.core.UserEntityRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserEntityRepositoryImpl implements UserEntityRepository {

    private SessionFactory sessionFactory;

    public UserEntityRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        UserEntity userEntity = session.find(UserEntity.class, username);
        session.close();
        return Optional.ofNullable(userEntity);

    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        Session session = sessionFactory.openSession();
        UserEntity userEntity = session.find(UserEntity.class, id);
        session.close();
        return Optional.ofNullable(userEntity);
    }

    @Override
    public void deleteById(Long id) {
        Session session = sessionFactory.openSession();
        session.remove(findById(id).orElseThrow(() -> new UserNotFoundException(id)));
        session.close();
    }

    @Override
    public UserEntity save(UserEntity user) {
        Session session = sessionFactory.openSession();
        session.persist(user);
        session.close();
        return user;
    }
}
