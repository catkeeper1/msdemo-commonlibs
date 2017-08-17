package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import org.ckr.msdemo.pagination.entity.User;
import org.ckr.msdemo.pagination.entity.UserWithRole;
import org.ckr.msdemo.pagination.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 * Created by yukai.a.lin on 8/14/2017.
 */
public class JpaRestPaginationServiceStaticTest {


    private static Logger LOG = LoggerFactory.getLogger(JpaRestPaginationServiceStaticTest.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManagerFactory entityManagerFactory;

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
