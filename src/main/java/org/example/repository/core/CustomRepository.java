package org.example.repository.core;

import java.util.List;
import java.util.Optional;

public interface CustomRepository<T, E> {

    List<E> findAll();

    Optional<E> findById(T id);

    E save(E entity);

    void deleteById(T id);

}
