package org.example.repository.core;

public interface FileStorage<T> extends Storage<T> {

    void parseMemoryFile();
}
