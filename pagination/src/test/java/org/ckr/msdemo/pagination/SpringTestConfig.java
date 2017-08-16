package org.ckr.msdemo.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class is only used to init spring boot container for DAO unit test case.
 * Annotation SpringBootApplication is needed for spring container initialization.
 */
@SpringBootApplication
public class SpringTestConfig {
    public static void main(String[] args) {
        SpringApplication.run(SpringTestConfig.class, args);
    }
}
