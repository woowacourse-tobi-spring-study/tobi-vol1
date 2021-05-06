package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
//        return new UserDao(getConnectionMaker());
        UserDao userDao = new UserDao();
        userDao.setConnectionMaker(getConnectionMaker());
        return userDao;
    }

    public AccountDao accountDao() {
        return new AccountDao(getConnectionMaker());
    }

    public MessageDao messageDao() {
        return new MessageDao(getConnectionMaker());
    }

    @Bean
    public ConnectionMaker getConnectionMaker() {
        ConnectionMaker connectionMaker = new NConnectionMaker();
        return connectionMaker;
    }
}
