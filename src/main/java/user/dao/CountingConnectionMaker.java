package user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    private ConnectionMaker realconnectionMaker;

    public CountingConnectionMaker(ConnectionMaker connectionMaker) {
        this.realconnectionMaker = connectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        return realconnectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
