package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean;

@Configuration
@EnableJpaRepositories("org.example.repository")
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new Jackson2ObjectMapperFactoryBean().getObject();
    }
}
