package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    private final ConnectionMaker connectionMaker;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return connectionMaker.makeNewConnection();
    }
}
