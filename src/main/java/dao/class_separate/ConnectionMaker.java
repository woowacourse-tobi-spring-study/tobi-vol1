package dao.class_separate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface ConnectionMaker {
    public Connection makeNewConnection() throws SQLException, ClassNotFoundException;
}
