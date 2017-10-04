package org.ckr.msdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is only used to init spring boot container for DAO unit test case.
 * Annotation SpringBootApplication is needed for spring container initialization.
 */
@SpringBootApplication(scanBasePackages = {"org.ckr.msdemo.pagination","org.ckr.msdemo.dbaccesstest"})
public class DbAccessTestApp {

    public static void main(String[] args) {

        SpringApplication.run(DbAccessTestApp.class, args);
    }

}


