package user.service;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import user.dao.UserDao;
import user.domain.User;

import java.util.List;

public class UserService {
    private UserDao userDao;
    private PlatformTransactionManager transactionManager;

    public UserService(UserDao userDao, PlatformTransactionManager transactionManager) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
    }

    public void add(User user) {
        user.checkNewbie();
        userDao.add(user);
    }

    public void upgradeLevels() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            upgradeAllUserLevels();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw e;
        }
    }

    private void upgradeAllUserLevels() {
        final List<User> users = userDao.getAll();
        for (User user : users) {
            if (user.canUpgradeLevel()) {
                upgradeLevel(user);
            }
        }
    }

    protected void upgradeLevel(User user) {
        user.upgradeLevel();
        userDao.update(user);
    }
}
