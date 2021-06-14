package user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import user.connection.ConnectionMaker;
import user.connection.SimpleConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDaoJdbc;
import user.service.UserService;

import javax.sql.DataSource;
import java.sql.Driver;

@Configuration
public class DaoFactory {
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
        return new SimpleConnectionMaker();
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

/*
UserDao의 객체 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 것!
오브젝트를 생성하는 책임을 가짐!
애플리케이션의 오브젝트를 구성하고, 그 관계를 정의하는 책임을 맡고 있음!
컴포넌트의 구조와 관계를 정의한 "설계도"
*/