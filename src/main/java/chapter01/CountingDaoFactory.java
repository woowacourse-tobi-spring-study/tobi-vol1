package chapter01;

import chapter01.dao.Connections;
import chapter01.dao.CountingConnectionsMaker;
import chapter01.dao.DConnections;
import chapter01.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connections());
    }

    @Bean
    public Connections connections() {
        return new CountingConnectionsMaker(realConnectionMaker());
    }

    @Bean
    public Connections realConnectionMaker() {
        return new DConnections();
    }
}
