package org.example.repository.core;

import java.util.List;
import java.util.Optional;

//TODO how I know  we should use repository or DAO because it the same thing.
public interface CustomRepository<T, E> {

    List<E> findAll();

    Optional<E> findById(T id);

    E save(E entity);

    void deleteById(T id);

    E update(E entity);

}
