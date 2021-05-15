package dao.counting;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker {
    int counter = 0;
    private ConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeNewConnection() throws SQLException, ClassNotFoundException {
        this.counter++;
        return realConnectionMaker.makeNewConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
