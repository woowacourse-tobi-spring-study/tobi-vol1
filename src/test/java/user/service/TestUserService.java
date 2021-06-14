package user.service;

import org.springframework.transaction.PlatformTransactionManager;
import user.dao.UserDao;
import user.domain.User;

public class TestUserService extends UserService {
    private String id;

    public TestUserService(UserDao userDao, PlatformTransactionManager platformTransactionManager, String id) {
        super(userDao, platformTransactionManager);
        this.id = id;
    }

    @Override
    protected void upgradeLevel(User user) {
        if (user.getId().equals(id)) {
            throw new TestUserServiceException();
        }
        super.upgradeLevel(user);
    }
}
