package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.Level;
import geon.hee.tobyspring.domain.User;
import geon.hee.tobyspring.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("userA", "박범진", "p1", Level.BASIC, 49, 0),
                new User("userB", "김나영", "p2", Level.BASIC, 50, 0),
                new User("userC", "신승환", "p3", Level.SILVER, 60, 29),
                new User("userD", "이상호", "p4", Level.SILVER, 60, 30),
                new User("userE", "오민규", "p5", Level.GOLD, 100, 100)
        );

        userDao.deleteAll();
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void upgradeLevels() {
        // given
        for (User user : users) {
            userDao.add(user);
        }

        // when
        userService.upgradeLevels();

        // then
        checkLevel(users.get(0), Level.BASIC);
        checkLevel(users.get(1), Level.SILVER);
        checkLevel(users.get(2), Level.SILVER);
        checkLevel(users.get(3), Level.GOLD);
        checkLevel(users.get(4), Level.GOLD);
    }

    private void checkLevel(User user, Level expectedLevel) {
        User userUpdate = userDao.get(user.getId());
        assertThat(userUpdate.getLevel()).isEqualTo(expectedLevel);
    }
}