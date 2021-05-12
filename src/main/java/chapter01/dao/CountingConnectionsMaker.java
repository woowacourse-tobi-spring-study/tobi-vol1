package chapter01.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionsMaker implements Connections {
    int counter = 0;
    private final Connections realConnectionMaker;

    public CountingConnectionsMaker(Connections realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeConnection() throws ClassNotFoundException, SQLException {
        counter++;
        return realConnectionMaker.makeConnection();
    }

    public int getCounter() {
        return counter;
    }
}
