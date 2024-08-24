package org.example.repository.core;

public interface Storage<T> {

    T get(Long id);

    T add(T t);

    T remove(T t);

    T update(T t);
}
