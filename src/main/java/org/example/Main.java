package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "org.example.*")
@EnableJpaRepositories
public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}