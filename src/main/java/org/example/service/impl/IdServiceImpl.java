package org.example.service.impl;

import jakarta.annotation.PostConstruct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import org.example.service.core.DatabasePathService;
import org.example.service.core.IdService;

public class IdServiceImpl implements IdService {

    private final DatabasePathService databasePathService;
    private String fileName;

    public IdServiceImpl(DatabasePathService databasePathService) {
        this.databasePathService = databasePathService;
    }

    @PostConstruct
    public void init() {
        this.fileName = databasePathService.getIdPath();
    }

    @Override
    public Long getId() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return Long.parseLong(scanner.nextLine());
    }

    @Override
    public void autoIncrement() {

        Long id = getId() + 1;

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(String.valueOf(id));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
