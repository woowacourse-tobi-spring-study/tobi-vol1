package domain;

import dao.ConnectionMaker;
import dao.MysqlConnection;
import dao.UserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {

    @Bean
    public UserDao userDao() {
        UserDao dao = new UserDao();
        return dao;
    }

    //추후 여러 DAO가 만들었을 때 중복 제거를 위한 메소드 분리
    @Bean
    public ConnectionMaker connectionMaker() {
        return new MysqlConnection();
    }
}
