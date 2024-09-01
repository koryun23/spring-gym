package org.example.repository.core;

import java.util.Optional;

public interface Storage<T> {

    T get(Long id);

    T add(T t);

    boolean remove(Long id);

    T update(T t);

    Optional<T> findById(Long id);
}
