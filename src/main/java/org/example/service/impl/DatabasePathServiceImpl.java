package org.example.service.impl;

import org.example.service.core.DatabasePathService;

public class DatabasePathServiceImpl implements DatabasePathService {

    private String entityPath;
    private String idPath;

    public DatabasePathServiceImpl(String entityPath, String idPath) {
        this.entityPath = entityPath;
        this.idPath = idPath;
    }

    @Override
    public String getEntityPath() {
        return entityPath;
    }

    @Override
    public String getIdPath() {
        return idPath;
    }

    @Override
    public String toString() {
        return "DatabasePathServiceImpl{" +
            "entityPath='" + entityPath + '\'' +
            ", idPath='" + idPath + '\'' +
            '}';
    }
}
