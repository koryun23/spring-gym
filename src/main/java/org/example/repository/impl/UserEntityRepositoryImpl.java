package org.example.repository.impl;

import java.util.Optional;
import org.example.entity.UserEntity;
import org.example.repository.core.UserEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserEntityRepositoryImpl implements UserEntityRepository {
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public UserEntity save(UserEntity user) {
        return null;
    }
}
