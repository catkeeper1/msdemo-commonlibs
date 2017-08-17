package org.ckr.msdemo.pagination;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.ckr.msdemo.pagination.entity.UserWithRole;
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

import java.util.List;

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

    public void testTemplate(String userName, String userDesc, String range, String SortBy,
                             int length, String firstName, int statusCode) {
        String url = "http://localhost:" + port + "/user/queryUsersWithRoles?userName=" + userName + "&userDesc=" + userDesc;

        HttpHeaders headers = new HttpHeaders();
        headers.add("Range", range);
        headers.add("SortBy", SortBy);
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<UserWithRole[]> response = testRestTemplate.exchange(url, HttpMethod.GET, entity, UserWithRole[].class);
        UserWithRole[] userWithRoles = response.getBody();

        for (UserWithRole userWithRole :
            userWithRoles) {
            LOG.info(userWithRole.toString());
        }
        assertThat(userWithRoles.length).isEqualTo(length);
        if(userWithRoles != null && userWithRoles.length > 0){
            assertThat(userWithRoles[0].getUserName()).isEqualTo(firstName);
        }

        MediaType contentType1 = response.getHeaders().getContentType();
        LOG.info(contentType1.toString());
        HttpStatus statusCode1 = response.getStatusCode();
        LOG.info("statusCode is {}", statusCode1.toString());
        assertThat(statusCode1.value()).isEqualTo(statusCode);
    }

    @Test
    public void testSample(){
        this.testTemplate("", "","items=1-20", "-userName",
            15, "DEF", 200);
    }
    @Test
    public void testPageSize(){
        this.testTemplate("", "","items=1-14", "-userName",
            14, "DEF", 200);
    }
    @Test
    public void testOrder(){
        this.testTemplate("", "","items=1-20", "+userName",
            15, "ABC", 200);
    }
    @Test
    public void testUserName(){
        this.testTemplate("ABC", "","items=1-20", "-userName",
            3, "ABC", 200);
    }

    @Test
    public void testUserDesc(){
        this.testTemplate("", "DEF","items=1-20", "-userName",
            1, "DEF", 200);
    }

    @Test
    public void testNoRecord(){
        this.testTemplate("a", "","items=1-20", "-userName",
            0, "DEF", 200);
    }
}
