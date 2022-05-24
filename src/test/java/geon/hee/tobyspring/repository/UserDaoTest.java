package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.config.DaoFactory;
import geon.hee.tobyspring.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = ac.getBean("userDao", UserDao.class);
    }

    @Test
    void add() throws SQLException {
        User user = new User();
        user.setId("geonhee");
        user.setName("성건희");
        user.setPassword("12341234!");

        userDao.add(user);

        User findUser = userDao.get(user.getId());

        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void get() {
    }
}