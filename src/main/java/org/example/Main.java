package org.example;

import org.example.config.Config;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    }
}