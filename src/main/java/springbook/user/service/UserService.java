package springbook.user.service;

import springbook.user.Level;
import springbook.user.User;
import springbook.user.dao.UserDao;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            upgradeLevel(user);
        }
    }

    public void upgradeLevel(User user) {
        if (user.canUpgradeLevel()) {
            user.upgradeLevel();
        }
        userDao.update(user);
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }
}
