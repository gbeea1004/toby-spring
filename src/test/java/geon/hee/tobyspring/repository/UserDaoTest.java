package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.Level;
import geon.hee.tobyspring.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    private User user1;

    private User user2;

    private User user3;

    @BeforeEach
    void setUp() {
        user1 = new User("userA", "성건희", "12341234!", Level.BASIC, 1, 0, "testA@naver.com");
        user2 = new User("userB", "지코", "asdf!", Level.SILVER, 55, 10, "testB@naver.com");
        user3 = new User("userC", "재키와이", "evz2!", Level.GOLD, 100, 40, "testC@naver.com");

        userDao.deleteAll();
        assertThat(userDao.getCount()).isEqualTo(0);
    }

    @Test
    void add() {
        // given

        // when
        userDao.add(user1);
        User findUser = userDao.get(user1.getId());

        // then
        checkSameUser(user1, findUser);
    }

    @Test
    void addAndGet() {
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        User findUser = userDao.get(user1.getId());
        checkSameUser(user1, findUser);
    }

    @Test
    void count() {
        userDao.add(user1);
        assertThat(userDao.getCount()).isEqualTo(1);

        userDao.add(user2);
        assertThat(userDao.getCount()).isEqualTo(2);

        userDao.add(user3);
        assertThat(userDao.getCount()).isEqualTo(3);
    }

    @Test
    void getUserFailure() {
        assertThatThrownBy(() -> userDao.get("unknown_id"))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void getAll() {
        List<User> users0 = userDao.getAll();
        assertThat(users0.size()).isEqualTo(0);

        userDao.add(user1);
        List<User> users1 = userDao.getAll();
        assertThat(users1.size()).isEqualTo(1);
        checkSameUser(user1, users1.get(0));

        userDao.add(user2);
        List<User> users2 = userDao.getAll();
        assertThat(users2.size()).isEqualTo(2);
        checkSameUser(user1, users2.get(0));
        checkSameUser(user2, users2.get(1));

        userDao.add(user3);
        List<User> users3 = userDao.getAll();
        assertThat(users3.size()).isEqualTo(3);
        checkSameUser(user1, users3.get(0));
        checkSameUser(user2, users3.get(1));
        checkSameUser(user3, users3.get(2));
    }

    @Test
    void duplicateKey() {
        // given

        // when
        userDao.add(user1);

        // then
        assertThatThrownBy(() -> userDao.add(user1)).isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void sqlExceptionTranslate() {
        try {
            userDao.add(user1);
            userDao.add(user1);
        } catch (DuplicateKeyException e) {
            SQLException sqlEx = (SQLException) e.getRootCause();
            SQLExceptionTranslator set = new SQLErrorCodeSQLExceptionTranslator(dataSource);

            assertThat(set.translate(null, null, sqlEx)).isInstanceOf(DuplicateKeyException.class);
        }
    }

    @Test
    void update() {
        // given
        userDao.add(user1);
        userDao.add(user2);

        // when
        user1.setName("수정된 이름");
        user1.setPassword("svcz!!");
        user1.setLevel(Level.GOLD);
        user1.setLogin(1000);
        user1.setRecommend(999);
        user1.setEmail("update@naver.com");

        userDao.update(user1);

        // then
        User findUser1 = userDao.get(user1.getId());
        checkSameUser(user1, findUser1);
        User findUser2 = userDao.get(user2.getId());
        checkSameUser(user2, findUser2);
    }

    private void checkSameUser(User user1, User user2) {
        assertThat(user1.getId()).isEqualTo(user2.getId());
        assertThat(user1.getName()).isEqualTo(user2.getName());
        assertThat(user1.getPassword()).isEqualTo(user2.getPassword());
        assertThat(user1.getLevel()).isEqualTo(user2.getLevel());
        assertThat(user1.getLogin()).isEqualTo(user2.getLogin());
        assertThat(user1.getRecommend()).isEqualTo(user2.getRecommend());
        assertThat(user1.getEmail()).isEqualTo(user2.getEmail());
    }
}