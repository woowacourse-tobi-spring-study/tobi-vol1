package user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import user.connection.ConnectionMaker;
import user.connection.TestConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDaoJdbc;
import user.service.UserService;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
public class DaoFactoryForTest {
    @Bean
    public UserService userService() {
        return new UserService(userDao());
    }

    @Bean
    public UserDaoJdbc userDao() {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new TestConnectionMaker();
    }

    @Bean
    public DataSource dataSource() {
        Driver h2Driver = new org.h2.Driver();
        return new SimpleDriverDataSource(
                h2Driver,
                "jdbc:h2:tcp://localhost/~/test",
                "sa",
                "");
    }
}

