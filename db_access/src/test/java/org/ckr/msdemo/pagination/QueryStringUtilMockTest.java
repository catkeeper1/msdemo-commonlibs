package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import mockit.Deencapsulation;
import org.ckr.msdemo.util.QueryStringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yukai.a.lin on 8/14/2017.
 */
public class QueryStringUtilMockTest {

    private static Logger LOG = LoggerFactory.getLogger(QueryStringUtilMockTest.class);

    @Test
    public void testAdjustQueryStringAll() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ " +
            "/*Desc| and u.Description like :Desc */";
        Set<String> params = new HashSet<>();
        params.add("userName");
        params.add("Desc");

        String parsedQuery =
            QueryStringUtil.adjustDynamicQueryString(rawQuery, params);
        assertThat(parsedQuery).isEqualTo("select u.a, u.b from User u where 1=1  and u.Name = :userName   " +
            "and u.Description like :Desc ");
    }

    @Test
    public void testAdjustQueryStringPartial() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| " +
            "and u.Description like :Desc */";
        Set<String> params = new HashSet<>();
        params.add("userName");
        String parsedQuery = QueryStringUtil.adjustDynamicQueryString(rawQuery, params);

        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1  and u.Name = :userName");
    }

    @Test
    public void testAdjustQueryStringNone() {
        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and " +
            "u.Description like :Desc */";
        Set<String> params = new HashSet<>();
        String parsedQuery = QueryStringUtil.adjustDynamicQueryString(rawQuery, params);
        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1");
    }

}
