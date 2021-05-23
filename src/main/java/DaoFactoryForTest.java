import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.connection.ConnectionMaker;
import user.connection.TestConnectionMaker;
import user.dao.UserDao;

@Configuration
public class DaoFactoryForTest {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
    }

    @Bean
    public ConnectionMaker connectionMaker() {
        return new TestConnectionMaker();
    }
}

