package org.example.dao.core;

public interface Dao<T> {
    T get(Long id);

    T save(T t);

    T update(T t);

    T delete(T t);
}