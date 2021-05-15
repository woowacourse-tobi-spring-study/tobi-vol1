package springbook.dao;

import org.springframework.context.annotation.Configuration;

@Configuration
public class CountingDaoFactory {

//    @Bean
//    public UserDao userDao() {
//        UserDao userDao = new UserDao();
//        userDao.setDataSource(connectionMaker());
//        return userDao;
//    }
//
//    @Bean
//    public ConnectionMaker connectionMaker() {
//        return new CountingConnectionMaker(realConnectionMaker());
//    }
//
//    @Bean
//    public ConnectionMaker realConnectionMaker() {
//        return new SimpleConnectionMaker();
//    }
}
