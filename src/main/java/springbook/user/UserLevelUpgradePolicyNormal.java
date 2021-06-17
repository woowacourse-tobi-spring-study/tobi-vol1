package springbook.user;

public class UserLevelUpgradePolicyNormal implements UserLevelUpgradePolicy{

    @Override
    public boolean canUpgradeLevel(User user) {
        switch (user.level) {
            case BASIC: return user.login >= MIN_LOGCOUNT_FOR_SILVER;
            case SILVER: return user.recommend >= MIN_RECOMMEND_FOR_GOLD;
            case GOLD: return false;
            default: throw new IllegalArgumentException("Unknown Level: " + user.level);
        }
    }

    @Override
    public void upgradeLevel(User user) {
        Level nextLevel = user.level.next();
        if (nextLevel == null) {
            throw new IllegalStateException(user.level + "은 업그레이드가 불가능합니다.");
        } else {
            user.setLevel(nextLevel);
        }
    }
}
