package springbook.user.service;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.util.List;

public class UserService {
    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
        List<User> all = userDao.getAll();
        all.forEach(user -> {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
                userDao.update(user);
            }
        });
    }
}
