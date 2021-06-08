package springbook.service;

import springbook.domain.user.Level;
import springbook.domain.user.User;

public class NormalUserLevelUpgradePolicy implements UserLevelUpgradePolicy {

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECCOMEND_FOR_GOLD = 30;

    @Override
    public boolean canUpgradeLevel(User user) {
        if (user.getLevel() == Level.BASIC) {
            return user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER;
        }
        if (user.getLevel() == Level.SILVER) {
            return user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD;
        }
        if (user.getLevel() == Level.GOLD) {
            return false;
        }
        throw new IllegalArgumentException("unknown level");
    }

    @Override
    public void upgradeLevel(User user) {
        user.upgradeLevel();
    }
}
