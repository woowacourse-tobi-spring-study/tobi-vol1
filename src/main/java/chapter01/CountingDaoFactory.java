package chapter01;

import chapter01.dao.Connections;
import chapter01.dao.CountingConnectionsMaker;
import chapter01.dao.DConnections;
import chapter01.dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class CountingDaoFactory {

    @Bean
    public UserDao userDao() {
        return new UserDao(dataSource());
    }

    @Bean
    public Connections connections() {
        return new CountingConnectionsMaker(realConnectionMaker());
    }

    @Bean
    public Connections realConnectionMaker() {
        return new DConnections();
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:23306/tobi?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }
}
