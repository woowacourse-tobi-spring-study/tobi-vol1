package chapter01.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connections {
    Connection makeConnection() throws ClassNotFoundException, SQLException;
}
