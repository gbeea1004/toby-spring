package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoTest {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new DaoFactory().userDao();
    }

    @Test
    void add() throws SQLException, ClassNotFoundException {
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