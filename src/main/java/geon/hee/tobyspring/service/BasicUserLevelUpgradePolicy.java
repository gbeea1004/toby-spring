package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.User;
import geon.hee.tobyspring.repository.UserDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasicUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    private final UserDao userDao;

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
