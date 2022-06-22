package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.Level;
import geon.hee.tobyspring.domain.User;

import static geon.hee.tobyspring.service.UserService.MIN_LOGIN_COUNT_FOR_SILVER;
import static geon.hee.tobyspring.service.UserService.MIN_RECOMMEND_FOR_GOLD;

public interface UserLevelUpgradePolicy {

    default boolean canUpgradeLevel(User user) {
        Level currentLevel = user.getLevel();
        switch (currentLevel) {
            case BASIC: return (user.getLogin() >= MIN_LOGIN_COUNT_FOR_SILVER);
            case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + currentLevel);
        }
    }

    void upgradeLevel(User user);
}
