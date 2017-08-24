package org.ckr.msdemo.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import javax.persistence.EntityManager;


@Configuration
public class SpringTestConfig {

    @Autowired
    EntityManager entityManager;

    @Bean
    public JpaRestPaginationService loadJpaRestPaginationService(){
        JpaRestPaginationService result = new JpaRestPaginationService();
        result.setEntityManager(this.entityManager);

        return result;
    }
}


