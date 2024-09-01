package org.example.repository.core;

import java.util.Map;

public interface FileStorage<T> {

    Map<Long, T> parseMemoryFile();

    void persist(Map<Long, T> storage);
}
