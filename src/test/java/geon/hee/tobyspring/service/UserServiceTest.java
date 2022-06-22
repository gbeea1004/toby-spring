package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.Level;
import geon.hee.tobyspring.domain.User;
import geon.hee.tobyspring.repository.UserDao;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static geon.hee.tobyspring.service.UserService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DataSource dataSource;

    private List<User> users;

    @BeforeEach
    void setUp() {
        users = Arrays.asList(
                new User("userA", "박범진", "p1", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER - 1, 0),
                new User("userB", "김나영", "p2", Level.BASIC, MIN_LOGIN_COUNT_FOR_SILVER, 0),
                new User("userC", "신승환", "p3", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD - 1),
                new User("userD", "이상호", "p4", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD),
                new User("userE", "오민규", "p5", Level.GOLD, 100, Integer.MAX_VALUE)
        );

        userDao.deleteAll();
    }

    @Test
    void bean() {
        assertThat(userService).isNotNull();
    }

    @Test
    void add() {
        // given
        User userWithLevel = users.get(4);
        User userWithoutLevel = users.get(0);
        userWithoutLevel.setLevel(null);

        // when
        userService.add(userWithLevel);
        userService.add(userWithoutLevel);

        User userWithLevelRead = userDao.get(userWithLevel.getId());
        User userWithoutLevelRead = userDao.get(userWithoutLevel.getId());

        // then
        assertThat(userWithLevelRead.getLevel()).isEqualTo(userWithLevel.getLevel());
        assertThat(userWithoutLevelRead.getLevel()).isEqualTo(Level.BASIC);
    }

    @Test
    void upgradeLevels() throws SQLException {
        // given
        for (User user : users) {
            userDao.add(user);
        }

        // when
        userService.upgradeLevels();

        // then
        checkLevelUpgraded(users.get(0), false);
        checkLevelUpgraded(users.get(1), true);
        checkLevelUpgraded(users.get(2), false);
        checkLevelUpgraded(users.get(3), true);
        checkLevelUpgraded(users.get(4), false);
    }

    @Test
    void upgradeAllOrNothing() {
        UserService testUserService = new TestUserService(new TestUserLevelUpgradePolicy(userDao, users.get(3).getId()), userDao, dataSource);
        for (User user : users) {
            userDao.add(user);
        }

        try {
            testUserService.upgradeLevels();
            fail("TestUserServiceException expected");
        } catch (TestUserServiceException | SQLException e) {
        }

        checkLevelUpgraded(users.get(1), false);
    }

    private void checkLevelUpgraded(User user, boolean upgraded) {
        User userUpdate = userDao.get(user.getId());
        if (upgraded) {
            assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel().nextLevel());
            return;
        }
        assertThat(userUpdate.getLevel()).isEqualTo(user.getLevel());
    }

    static class TestUserService extends UserService {

        public TestUserService(UserLevelUpgradePolicy userLevelUpgradePolicy, UserDao userDao, DataSource dataSource) {
            super(userLevelUpgradePolicy, userDao, dataSource);
        }
    }

    @AllArgsConstructor
    static class TestUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

        private final UserDao userDao;
        private String id;

        @Override
        public boolean canUpgradeLevel(User user) {
            Level currentLevel = user.getLevel();
            switch (currentLevel) {
                case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
                case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
                case GOLD: return false;
                default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
            }
        }

        @Override
        public void upgradeLevel(User user) {
            if (user.getId().equals(id)) {
                throw new TestUserServiceException();
            }

            user.upgradeLevel();
            userDao.update(user);
        }
    }

    static class TestUserServiceException extends RuntimeException {
    }
}