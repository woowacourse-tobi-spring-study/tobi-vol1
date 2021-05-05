package chapter01.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DConnections implements Connections {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        //D사에서 커넥션을 생성해서 반환
        return null;
    }
}
