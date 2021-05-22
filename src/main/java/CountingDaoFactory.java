import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.connection.ConnectionMaker;
import user.connection.CountingConnectionMaker;
import user.connection.SimpleConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDao;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(jdbcContext(), connectionMaker());
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new CountingConnectionMaker(new SimpleConnectionMaker());
    }
}
