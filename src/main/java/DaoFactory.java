import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import user.connection.ConnectionMaker;
import user.connection.SimpleConnectionMaker;
import user.dao.JdbcContext;
import user.dao.UserDaoJdbc;

import javax.sql.DataSource;
import java.sql.Driver;
import java.sql.SQLException;

@Configuration
public class DaoFactory {
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
        return new SimpleConnectionMaker();
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

/*
UserDao의 객체 생성 방법을 결정하고, 그렇게 만들어진 오브젝트를 돌려주는 것!
오브젝트를 생성하는 책임을 가짐!
애플리케이션의 오브젝트를 구성하고, 그 관계를 정의하는 책임을 맡고 있음!
컴포넌트의 구조와 관계를 정의한 "설계도"
*/