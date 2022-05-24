package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.config.CountingDaoFactory;
import geon.hee.tobyspring.config.connection.CountingConnectionMaker;
import geon.hee.tobyspring.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoConnectionCountingTest {

    @Test
    void countingTest() throws SQLException {
        // given
        ApplicationContext ac = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao userDao = ac.getBean("userDao", UserDao.class);

        User user = new User();
        user.setId("geonhee");
        user.setName("성건희");
        user.setPassword("12341234!");

        CountingConnectionMaker ccm = ac.getBean("connectionMaker", CountingConnectionMaker.class);

        // when
        userDao.add(user);
        userDao.get(user.getId());

        // then
        assertThat(ccm.getCounter()).isEqualTo(2);
    }
}
