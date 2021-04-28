package springbook.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DUserDao extends UserDao {

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        // D사 DB Connection 생성코드
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager
                .getConnection("jdbc:mysql://localhost/tobi_db", "root", "root");
    }
}
