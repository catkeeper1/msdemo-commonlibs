package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import mockit.Expectations;
import org.apache.catalina.core.ApplicationContext;
import org.ckr.msdemo.pagination.entity.UserWithRole;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.swing.Spring;

/**
 * Created by yukai.a.lin on 8/14/2017.
 */
public class JpaRestPaginationServiceMockTest {

    private static Logger LOG = LoggerFactory.getLogger(JpaRestPaginationServiceMockTest.class);


    @Test
    public void testabc(){

        new Expectations(){{

        }};
//
//        JpaRestPaginationService jpaRestPaginationService = (JpaRestPaginationService) SpringUtil.getBean(JpaRestPaginationService.class);
//        Map<String, Object> params = new HashMap<String, Object>();
//
//        if (!StringUtils.isEmpty("")) {
//            params.put("userName", "");
//        }
//        if (!StringUtils.isEmpty("")) {
//            params.put("userDesc", "%" + "" + "%");
//        }
//        Function<Object[], UserWithRole> mapper = new Function<Object[], UserWithRole>() {
//            @Override
//            public UserWithRole apply(Object[] row) {
//                UserWithRole view = new UserWithRole();
//                view.setUserName((String) row[0]);
//                view.setUserDescription((String) row[1]);
//                view.setLocked(((Boolean) row[2]));
//                view.setPassword((String) row[3]);
//                view.setRoleCode((String) row[4]);
//                view.setRoleDescription((String) row[5]);
//                return view;
//            }
//        };
//
//        List<UserWithRole> result = jpaRestPaginationService.query("", params, mapper);
    }

}
