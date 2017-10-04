package org.ckr.msdemo.dao;

import org.ckr.msdemo.dbaccesstest.dao.UserDao;
import org.ckr.msdemo.dbaccesstest.entity.UserWithRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BaseJpaDaoDbTests {

    @Autowired
    private UserDao userDao;

    @Test
    public void testExecuteDynamicQuery() {

        List<UserWithRole> resultList = userDao.queryUsersWithRoles("ABC", "");

        assertThat(resultList.size()).isEqualTo(3);
    }
}
