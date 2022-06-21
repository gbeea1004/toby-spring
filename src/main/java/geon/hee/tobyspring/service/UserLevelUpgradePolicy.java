package geon.hee.tobyspring.service;

import geon.hee.tobyspring.domain.User;

public interface UserLevelUpgradePolicy {

    boolean canUpgradeLevel(User user);

    void upgradeLevel(User user);
}
