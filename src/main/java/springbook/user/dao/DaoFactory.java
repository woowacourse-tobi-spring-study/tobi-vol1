package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public static UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    //TODO Connection 도 팩터리 클래스를 만든다면?
    @Bean
    public static ConnectionMaker connectionMaker() {
        return new SimpleConnectionMaker();
    }
}
