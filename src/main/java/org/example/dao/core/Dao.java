package org.example.dao.core;

import java.util.Optional;

public interface Dao<T> {

    T get(Long id);

    T save(T t);

    T update(T t);

    boolean delete(Long id);

    Optional<T> findById(Long id);
}
