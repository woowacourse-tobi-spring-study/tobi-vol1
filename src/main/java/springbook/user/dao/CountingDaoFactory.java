package springbook.user.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbook.user.dao.connection.CountingConnectionMaker;
import springbook.user.dao.connection.SimpleConnectionMaker;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDaoJdbc userDao() {
        return new UserDaoJdbc();
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(realConnectionMaker());
    }

    @Bean
    public ConnectionMaker realConnectionMaker() {
        return new SimpleConnectionMaker();
    }
}
