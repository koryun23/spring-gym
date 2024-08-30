package org.example.repository.core;

public interface FileStorage<T> {

    void parseMemoryFile();

    void persist();
}
