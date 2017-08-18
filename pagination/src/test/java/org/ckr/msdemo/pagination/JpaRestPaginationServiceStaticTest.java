package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yukai.a.lin on 8/14/2017.
 */
public class JpaRestPaginationServiceStaticTest {


    private static Logger LOG = LoggerFactory.getLogger(JpaRestPaginationServiceStaticTest.class);

    @Test
    public void testAdjustQueryStringAll() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "abc");
        params.put("Desc", "def");
        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
        assertThat(parsedQuery).isEqualTo("select u.a, u.b from User u where 1=1  and u.Name = :userName   and u.Description like :Desc ");
    }

    @Test
    public void testAdjustQueryStringPartial() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", "abc");
        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1  and u.Name = :userName");
    }

    @Test
    public void testAdjustQueryStringNone() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
        Map<String, Object> params = new HashMap<>();
        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1");
    }

}
