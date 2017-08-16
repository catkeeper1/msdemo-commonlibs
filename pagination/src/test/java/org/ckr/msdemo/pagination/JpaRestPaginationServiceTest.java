package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

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

/**
 * Created by yukai.a.lin on 8/14/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JpaRestPaginationServiceTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    private static Logger LOG = LoggerFactory.getLogger(JpaRestPaginationService.class);


    @Test
    public void testJpaservice() throws Exception {
        String url = "http://localhost:" + port + "/user/queryUsersWithRoles?userName=&userDesc=";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Range", "items=1-10");
        headers.add("SortBy", "-userName");
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<String>(headers);


        ResponseEntity<String> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String objects = response.getBody();
        LOG.info(objects);
        MediaType contentType1 = response.getHeaders().getContentType();
        LOG.info(contentType1.toString());
        LOG.info(contentType1.getSubtype());
        LOG.info(contentType1.getType());
        LOG.info(contentType1.getCharset().displayName());
        HttpStatus statusCode1 = response.getStatusCode();
        LOG.info("statusCode is {}", statusCode1.toString());
        assertThat(statusCode1.value()).isEqualTo(200);
        LOG.info("-----------------------------------------");
        LOG.info("-----------------------------------------");

        ResponseEntity<String> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port
            + "/user/queryUsersWithRoles?userName=&userDesc=", String.class);
        String body = responseEntity.getBody();
//        for (UserWithRole userWithRole :
//            objects) {
        LOG.info(body);
//        }
        MediaType contentType = responseEntity.getHeaders().getContentType();
        LOG.info(contentType.toString());
        LOG.info(contentType.getSubtype());
        LOG.info(contentType.getType());
        LOG.info(contentType.getCharset().displayName());
        HttpStatus statusCode = responseEntity.getStatusCode();
        LOG.info("statusCode is {}", statusCode.toString());
        assertThat(statusCode.value()).isEqualTo(200);

    }

//    @Test
//    public void testfindAlluser() {
//        Query query = entityManagerFactory
//            .createEntityManager()
//            .createQuery("select u.userName, u.userDescription, u.locked, g.roleCode, g.roleDescription from User u left join u.roles as g where 1=1");
//        List<UserWithRole> userWithRoles = (List<UserWithRole>)query.getResultList();
//        for (UserWithRole user:
//             userWithRoles) {
//            LOG.info(user.toString());
//        }
//    }
//
//    @Test
//    public void testAdjustQueryStringAll() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        params.put("userName", "abc");
//        params.put("Desc", "def");
//        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
//        assertThat(parsedQuery).isEqualTo("select u.a, u.b from User u where 1=1  and u.Name = :userName   and u.Description like :Desc ");
//    }
//
//    @Test
//    public void testAdjustQueryStringPartial() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        params.put("userName", "abc");
//        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
//        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1  and u.Name = :userName");
//    }
//
//    @Test
//    public void testAdjustQueryStringNone() {
//        String rawQuery = "select u.a, u.b from User u where 1=1 /*userName| and u.Name = :userName */ /*Desc| and u.Description like :Desc */";
//        Map<String, Object> params = new HashMap<>();
//        String parsedQuery = JpaRestPaginationService.adjustQueryString(rawQuery, params);
//        assertThat(parsedQuery).contains("select u.a, u.b from User u where 1=1");
//    }


//    @Test
//    public void doQueryTest() {
//        List<UserWithRole> result = this.testQuery("ABC4", "ABC", "items=1-10", "");
//        LOG.info("query result is {}", result);
//        assertThat(result.size()).isEqualTo(2);
//        assertThat(result.get(0).getUserName()).isEqualTo("ABC4");
//    }
//
//    @Test
//    public void findAllTest() {
//        List<User> users = userRepository.findAll();
//        assertThat(users.size()).isEqualTo(8);
//    }
}
