package org.example.repository.core;

public interface Storage<T> {

    T get(Long id);

    T add(T t);

    boolean remove(Long id);

    T update(T t);
}
