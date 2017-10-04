package org.ckr.msdemo.repository;

import org.ckr.msdemo.dbaccesstest.annotation.DbUnitTest;
import org.ckr.msdemo.dbaccesstest.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DbUnitTest
public class UserRepositoryDbTests {

    @Autowired
    private UserRepository userRepository;




    @Test
    public void testfindExistingByUserName() {
        this.userRepository.findAll();

    }



}