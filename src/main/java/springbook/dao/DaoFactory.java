package springbook.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import springbook.dao.user.UserDao;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setDataSource(dataSource());
        return userDao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        simpleDriverDataSource.setUrl("jdbc:mysql://localhost:13306/tobi");
        simpleDriverDataSource.setUsername("root");
        simpleDriverDataSource.setPassword("root");
        return simpleDriverDataSource;
    }
}
