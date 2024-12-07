package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@EnableJpaRepositories("org.example.repository")
@PropertySource(value = {"classpath:application.properties",
    "classpath:application-dev.properties",
    "classpath:application-local.properties",
    "classpath:application-prod.properties",
    "classpath:application-stg.properties"})
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper().findAndRegisterModules();
    }

    /**
     * Obtaining a data source bean.
     */
    @Bean
    public DataSource dataSource(@Value("${spring.flyway.user}") String username,
                                 @Value("${spring.flyway.password}") String password,
                                 @Value("${spring.flyway.url}") String url,
                                 @Value("${spring.flyway.driver}") String driver) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driver);

        return dataSource;
    }

    /**
     * Bean factory method for database migration.
     */
    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure().dataSource(dataSource).baselineOnMigrate(true).load();
        flyway.migrate();
        return flyway;
    }
}
