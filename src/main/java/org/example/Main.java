package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication(exclude = { SpringDataWebAutoConfiguration.class })
public class Main {
    /**
     * Main method.
     */
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        //PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
    }
}