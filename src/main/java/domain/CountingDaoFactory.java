//package domain;
//
//import dao.ConnectionMaker;
//import dao.CountingConnectionMaker;
//import dao.MysqlConnection;
//import dao.UserDao;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class CountingDaoFactory {
//    부가기능 구현을 위한 Factory 클래스
//    @Bean
//    public UserDao userDao() {
//        return new UserDao(connectionMaker());
//    }
//
//    @Bean
//    public ConnectionMaker connectionMaker() {
//        return new CountingConnectionMaker(realConnectionMaker());
//    }
//
//    @Bean
//    public ConnectionMaker realConnectionMaker() {
//        return new MysqlConnection();
//    }
//}
