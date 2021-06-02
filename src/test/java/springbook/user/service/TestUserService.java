package springbook.user.service;

import org.springframework.transaction.PlatformTransactionManager;
import springbook.user.dao.UserDao;
import springbook.user.domain.User;

public class TestUserService extends UserServiceImpl {
    private String id;

    public TestUserService(UserDao userDao, UserLevelUpgradePolicy userLevelUpgradePolicy, PlatformTransactionManager platformTransactionManager, String id) {
        super(userDao, userLevelUpgradePolicy, platformTransactionManager);
        this.id = id;
    }

    @Override
    protected void upgradeLevel(User user) {
        if(user.getId().equals(this.id)){
            throw new TestUserServiceException("테스트 오류입니다");
        }
        super.upgradeLevel(user);
    }

}
