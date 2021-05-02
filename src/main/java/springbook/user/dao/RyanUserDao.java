package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.*;

public class RyanUserDao {
    /*
    관심사
    DB와 연결을 위한 커넥션
    사용자 등록을 위해 DB에 보낼 Statement를 만들고 실행
    커넥션을 닫아주는 것
     */
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost/springbook", "spring", "book");
        return c;
    }
}
