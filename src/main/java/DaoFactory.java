import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.connection.ConnectionMaker;
import user.connection.SimpleConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDao;

@Configuration
public class DaoFactory {
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
        return new SimpleConnectionMaker();
    }
}

/*
UserDao의 객체 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 것!
오브젝트를 생성하는 책임을 가짐!
애플리케이션의 오브젝트를 구성하고, 그 관계를 정의하는 책임을 맡고 있음!
컴포넌트의 구조와 관계를 정의한 "설계도"
*/