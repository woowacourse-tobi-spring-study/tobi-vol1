package chapter01.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleConnectionMasker {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:23306/tobi?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8", "root", "root");
    }
}
