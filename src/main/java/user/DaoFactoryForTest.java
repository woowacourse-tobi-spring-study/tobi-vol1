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
import java.sql.SQLException;

@Configuration
public class DaoFactoryForTest {
    @Bean
    public UserService userService() throws SQLException {
        return new UserService(userDao());
    }

    @Bean
    public UserDaoJdbc userDao() throws SQLException {
        return new UserDaoJdbc(dataSource());
    }

    @Bean
    public JdbcContext jdbcContext() throws SQLException {
        return new JdbcContext(dataSource());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new TestConnectionMaker();
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        Driver h2Driver = new org.h2.Driver();
        return new SimpleDriverDataSource(
                h2Driver,
                "jdbc:h2:tcp://localhost/~/test",
                "sa",
                "");
    }
}

