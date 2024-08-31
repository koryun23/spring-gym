package org.example.service.impl;

import org.example.service.core.IdService;

import java.io.*;
import java.util.Scanner;

public class IdServiceImpl implements IdService {

    private final String file;

    public IdServiceImpl(String file) {
        this.file = file;
    }

    @Override
    public Long getId() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File(file));
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
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(String.valueOf(id));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
