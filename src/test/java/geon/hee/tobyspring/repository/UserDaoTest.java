package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.config.DaoFactory;
import geon.hee.tobyspring.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

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
        // given
        User user = new User("geonhee", "성건희", "12341234!");

        // when
        userDao.add(user);
        User findUser = userDao.get(user.getId());

        // then
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        User user = new User("geonhee", "성건희", "12341234!");

        userDao.add(user);
        assertThat(userDao.getCount()).isEqualTo(1);

        User findUser = userDao.get(user.getId());
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getName()).isEqualTo(user.getName());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
    }

    @Test
    void count() throws SQLException {
        User user1 = new User("userA", "성건희", "12341234!");
        User user2 = new User("userB", "지코", "asdf!");
        User user3 = new User("userC", "재키와이", "evz2!");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getUserFailure() throws SQLException {
        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);

        Assertions.assertThatThrownBy(() -> userDao.get("unknown_id"))
                  .isInstanceOf(EmptyResultDataAccessException.class);
    }
}