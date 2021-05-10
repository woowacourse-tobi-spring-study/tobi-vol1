package chapter01;

import chapter01.dao.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(connections());
    }

    @Bean
    public Connections connections() {
        return new DConnections();
    }
}