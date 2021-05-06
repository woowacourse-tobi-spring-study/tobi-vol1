package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao UserDao() {
        UserDao userDao = new UserDao();
        userDao.setSimpleConnectionMaker(connectionMaker());
        return userDao;
    }

    @Bean
    public SimpleConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public SimpleConnectionMaker realConnectionMaker() {
        return new BaeminSimpleConnectionMaker();
    }
}
