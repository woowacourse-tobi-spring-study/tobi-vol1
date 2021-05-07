package user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao {
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //N사 DB connection 생성코드
        return null;
    }
}
