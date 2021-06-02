package springbook.user.service;

import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void upgradeLevels() {
        List<User> all = userDao.getAll();
        all.forEach(this::upgradeLevel);
    }

    private void upgradeLevel(User user) {
        Level before = user.getLevel();
        user.upgradeLevel();
        Level after = user.getLevel();
        if (before != after) {
            userDao.update(user);
        }
    }
}
