package springbook.user.service;

import springbook.user.domain.Level;
import springbook.user.domain.User;

public class DefaultUpgradePolicy implements UserLevelUpgradePolicy{

    @Override
    public boolean canUpgradeLevel(User user) {
        Level before = user.getLevel();
        user.upgradeLevel();
        Level after = user.getLevel();
        user.setLevel(before);
        return before != after;
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
    }
}
