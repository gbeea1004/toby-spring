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
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() throws SQLException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DaoFactory.class);
        userDao = ac.getBean("userDao", UserDao.class);

        user1 = new User("userA", "성건희", "12341234!");
        user2 = new User("userB", "지코", "asdf!");
        user3 = new User("userC", "재키와이", "evz2!");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    void add() throws SQLException {
        // given

        // when
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());

        // then
        assertThat(findUser.getId()).isEqualTo(user1.getId());
        assertThat(findUser.getName()).isEqualTo(user1.getName());
        assertThat(findUser.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    void addAndGet() throws SQLException {
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        User findUser = userDao.get(user1.getId());
        assertThat(findUser.getId()).isEqualTo(user1.getId());
        assertThat(findUser.getName()).isEqualTo(user1.getName());
        assertThat(findUser.getPassword()).isEqualTo(user1.getPassword());
    }

    @Test
    void count() throws SQLException {
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getUserFailure() {
        Assertions.assertThatThrownBy(() -> userDao.get("unknown_id"))
                  .isInstanceOf(EmptyResultDataAccessException.class);
    }
}