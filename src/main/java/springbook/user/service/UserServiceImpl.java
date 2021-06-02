package springbook.user.service;

import springbook.user.dao.UserDao;
import springbook.user.domain.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    public UserServiceImpl(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userDao = userDao;
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    @Override
    public void upgradeLevels() {
        List<User> all = userDao.getAll();
        all.forEach(user -> {
            upgradeLevel(user);
        });
    }

    protected void upgradeLevel(User user) {
        if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
            userLevelUpgradePolicy.upgradeLevel(user);
            userDao.update(user);
        }
    }
}
