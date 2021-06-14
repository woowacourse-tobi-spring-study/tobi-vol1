package user.service;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import user.dao.UserDao;
import user.domain.User;

import java.util.List;

public class UserService {
    private UserDao userDao;
    private PlatformTransactionManager transactionManager;
    private MailSender mailSender;

    public UserService(UserDao userDao, PlatformTransactionManager transactionManager, MailSender mailSender) {
        this.userDao = userDao;
        this.transactionManager = transactionManager;
        this.mailSender = mailSender;
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
        sendUpgradeEmail(user);
    }

    private void sendUpgradeEmail(User user) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setFrom("useradmin@ksug.org");
        mailMessage.setSubject("Upgrade 안내");
        mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다");

        mailSender.send(mailMessage);
    }

}
