package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao UserDao() {
        return UserDao.getInstance();
    }

    @Bean
    public SimpleConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(new BaeminSimpleConnectionMaker());
    }
}
