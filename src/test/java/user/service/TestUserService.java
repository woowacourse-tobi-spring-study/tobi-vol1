package user.service;

import user.dao.UserDao;
import user.domain.User;

public class TestUserService extends UserService {
    private String id;

    public TestUserService(UserDao userDao, String id) {
        super(userDao);
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
