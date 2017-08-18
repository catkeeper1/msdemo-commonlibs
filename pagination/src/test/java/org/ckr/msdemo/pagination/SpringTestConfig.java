package org.ckr.msdemo.pagination;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

/**
 * This class is only used to init spring boot container for DAO unit test case.
 * Annotation SpringBootApplication is needed for spring container initialization.
 */
@SpringBootApplication(scanBasePackages = "org.ckr.msdemo.pagination")
public class SpringTestConfig {
    private static ConfigurableApplicationContext applicationContext = null;

    public void main(String args[]) {
        SpringApplication app = new SpringApplication(SpringTestConfig.class);
        app.setWebEnvironment(false);

        applicationContext = app.run(new String[] {});
    }

    public static Object getBean(Class clazz) {
        Map map = applicationContext.getBeansOfType(clazz);
        return map.values().iterator().next();
    }
}


