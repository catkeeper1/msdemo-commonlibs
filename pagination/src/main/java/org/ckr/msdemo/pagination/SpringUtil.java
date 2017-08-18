package org.ckr.msdemo.pagination;

/**
 * Created by yukai.a.lin on 8/18/2017.
 */

import org.springframework.context.ApplicationContext;

import java.util.Map;

public class SpringUtil {

    private static ApplicationContext ac = null;

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static void setApplicationContext(ApplicationContext ac) {
        SpringUtil.ac = ac;
    }

    public static Object getBean(String beanName) {
        return getApplicationContext().getBean(beanName);
    }

    public static Object getBean(Class clazz) {
        Map map = getApplicationContext().getBeansOfType(clazz);

        return map.values().iterator().next();
    }

}
