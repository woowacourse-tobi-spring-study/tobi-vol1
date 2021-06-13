import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import user.connection.ConnectionMaker;
import user.connection.CountingConnectionMaker;
import user.connection.SimpleConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDaoJdbc;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;

@Configuration
public class CountingDaoFactory {
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
        return new CountingConnectionMaker(new SimpleConnectionMaker());
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        Driver mysqlDriver = new com.mysql.cj.jdbc.Driver();
        return new SimpleDriverDataSource(
                mysqlDriver,
                "jdbc:mysql://localhost:13306/springbook",
                "root",
                "root");
    }
}
