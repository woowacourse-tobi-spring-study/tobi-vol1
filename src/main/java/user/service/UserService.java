package user.service;

import user.dao.UserDao;
import user.domain.User;

import java.util.List;

public class UserService {
    UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) {
        user.checkNewbie();
        userDao.add(user);
    }

    public void upgradeLevels() {
        final List<User> users = userDao.getAll();
        for (User user : users) {
            if (user.upgradeLevel()) {
                userDao.update(user);
            }
        }
    }
}
