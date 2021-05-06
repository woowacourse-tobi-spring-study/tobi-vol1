package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
    @Bean
    public UserDao UserDao() {
        return new UserDao(connectionMaker());
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
