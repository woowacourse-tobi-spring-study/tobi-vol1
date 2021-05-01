package dao.abstract_class;

import java.sql.Connection;
import java.sql.SQLException;

public class DUserDao extends UserDao {
    
    // mySQL 연결
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        return null;
    }
}
