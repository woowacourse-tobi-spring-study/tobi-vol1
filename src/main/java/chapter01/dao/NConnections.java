package chapter01.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NConnections implements Connections {
    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        //N사에서 커넥션을 생성해버 반환
        return null;
    }
}
