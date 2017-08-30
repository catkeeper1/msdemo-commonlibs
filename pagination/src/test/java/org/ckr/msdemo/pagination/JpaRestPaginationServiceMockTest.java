package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import mockit.Deencapsulation;
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
    public void testAdjustQueryStringAll() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ " +
            "/*Desc| and u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "abc");
        params.put("Desc", "def");
        //Using Deencapsulation.invoke to test private method
        String parsedQuery = Deencapsulation.invoke(JpaRestPaginationService.class, "adjustQueryString",
            rawQuery, params);
        assertThat(parsedQuery).isEqualTo("select u.a, u.b from User u where 1=1  and u.Name = :userName   " +
            "and u.Description like :Desc ");
    }

    @Test
    public void testAdjustQueryStringPartial() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| " +
            "and u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "abc");
        String parsedQuery = Deencapsulation.invoke(JpaRestPaginationService.class, "adjustQueryString",
            rawQuery, params);
        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1  and u.Name = :userName");
    }

    @Test
    public void testAdjustQueryStringNone() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and " +
            "u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        String parsedQuery = Deencapsulation.invoke(JpaRestPaginationService.class, "adjustQueryString",
            rawQuery, params);
        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1");
    }

}
