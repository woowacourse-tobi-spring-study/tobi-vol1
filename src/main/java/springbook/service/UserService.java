package springbook.service;

import org.springframework.stereotype.Service;
import springbook.dao.UserDao;
import springbook.domain.user.Level;
import springbook.domain.user.User;

@Service
public class UserService {

    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void upgradeLevels() {
        userDao.getAll()
                .forEach(user -> {
                    if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                        userLevelUpgradePolicy.upgradeLevel(user);
                        userDao.update(user);
                    }
                });
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.addUser(user);
    }
}
