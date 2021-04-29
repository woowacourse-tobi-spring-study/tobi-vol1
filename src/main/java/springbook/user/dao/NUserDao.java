package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//public class NUserDao extends UserDao {
//
//    @Override
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        return DriverManager.getConnection(
//            "jdbc:mysql://localhost:13306/toby"
//                + "?serverTimezone=UTC&characterEncoding=UTF-8&rewriteBatchedStatements=true",
//            "root", "root");
//    }
//}
