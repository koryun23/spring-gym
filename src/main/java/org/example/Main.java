package org.example;

import java.util.Optional;
import org.example.config.Config;
import org.example.entity.UserEntity;
import org.example.repository.core.UserEntityRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    /**
     * Main method.
     *
     * @param args Runtime Arguments.
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        UserEntityRepository repo = context.getBean(UserEntityRepository.class);
        repo.save(new UserEntity("first", "last", "username", "password", true));
        //Optional<UserEntity> optionalUser = repo.findById(3L);
        //System.out.println(optionalUser);
//        repo.save(new UserEntity(
//            "first", "last", "username", "password", true
//        ));
//        Optional<UserEntity> user = repo.findById(1L);
//        System.out.println(user);
        //System.out.println(user);
    }
}