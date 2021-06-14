package user.service;

import org.springframework.mail.MailSender;
import org.springframework.transaction.PlatformTransactionManager;
import user.dao.UserDao;
import user.domain.User;

public class TestUserService extends UserService {
    private String id;

    public TestUserService(UserDao userDao, PlatformTransactionManager platformTransactionManager, MailSender mailSender, String id) {
        super(userDao, platformTransactionManager, mailSender);
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
