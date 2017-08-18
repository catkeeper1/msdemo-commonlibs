package org.ckr.msdemo.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

/**
 * Created by yukai.a.lin on 8/18/2017.
 */
@Configuration
public class PaginationConfig {

    @Autowired
    EntityManager entityManager;

    @Bean
    public EntityManager loadEntityManager() {
        return this.entityManager;
    }

    @Bean
    public JpaRestPaginationService loadJpaRestPaginationService(){
        return new JpaRestPaginationService();
    }
}
