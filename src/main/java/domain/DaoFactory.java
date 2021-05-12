package domain;

import dao.ConnectionMaker;
import dao.MysqlConnection;
import dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao dao = new UserDao();
        dao.setDataSource(dataSource());
        return dao;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        dataSource.setDriverClass(com.mysql.cj.jdbc.Driver.class);
        dataSource.setUrl("jdbc:mysql://localhost:13306/springbook");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        return dataSource;
    }

    //추후 여러 DAO가 만들었을 때 중복 제거를 위한 메소드 분리
    @Bean

    public ConnectionMaker connectionMaker() {
        return new MysqlConnection();
    }
}
