package springbook.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import springbook.dao.UserDao;
import springbook.domain.user.Level;
import springbook.domain.user.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private UserLevelUpgradePolicy userLevelUpgradePolicy;
    private MailSender mailSender;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void upgradeLevels() {
        userDao.getAll()
                .forEach(this::upgradeLevel);
    }

    protected void upgradeLevel(User user) {
        if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
            userLevelUpgradePolicy.upgradeLevel(user);
            userDao.update(user);
            sendEmail(user);
        }
    }

    private void sendEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setFrom("useradmin@ksug.org");
        simpleMailMessage.setSubject("Upgrade 안내");
        simpleMailMessage.setText("사용자님의 등급이 " + user.getLevel().name());
        mailSender.send(simpleMailMessage);
    }

    @Override
    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.addUser(user);
    }

    @Override
    public User get(String id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public void deleteAll() {
        userDao.deleteAll();
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }
}
