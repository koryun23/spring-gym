package org.example.config;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@PropertySource("classpath:application.properties")
@Configuration
@ComponentScan("org.example")
public class HibernateConfig {

    /**
     * Bean factory method for obtaining an instance of SessionFactory.
     */
    @Bean
    public SessionFactory sessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        ServiceRegistry registry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();
        return configuration.buildSessionFactory();
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
        //dataSource.setSchema("classpath:/db/migration/V1__migration.sql");

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
