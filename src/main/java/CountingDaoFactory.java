import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.domain.ConnectionMaker;
import user.domain.CountingConnectionMaker;
import user.domain.SimpleConnectionMaker;
import user.domain.UserDao;

@Configuration
public class CountingDaoFactory {
    @Bean
    public UserDao userDao() {
        return new UserDao(connectionMaker());
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
