package org.ckr.msdemo.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * This class is only used to init spring boot container for DAO unit test case.
 * Annotation SpringBootApplication is needed for spring container initialization.
 */
@SpringBootApplication(scanBasePackages = "org.ckr.msdemo.pagination")
public class SpringTestApp {

    public static void main(String[] args) {

        SpringApplication.run(SpringTestApp.class, args);
    }

}


