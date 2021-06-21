package springbook.user;

public interface UserLevelUpgradePolicy {
    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    boolean canUpgradeLevel(User user);
    void upgradeLevel(User user);
}
