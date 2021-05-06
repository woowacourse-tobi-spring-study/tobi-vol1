package springbook.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements SimpleConnectionMaker {
    private int counter = 0;
    private SimpleConnectionMaker realConnectionMaker;

    public CountingConnectionMaker(SimpleConnectionMaker realConnectionMaker) {
        this.realConnectionMaker = realConnectionMaker;
    }

    @Override
    public Connection makeNewConnection() throws ClassNotFoundException, SQLException {
        this.counter++;
        System.out.println(this.counter + "번 dao에 접근함");
        return realConnectionMaker.makeNewConnection();
    }

    public int getCounter() {
        return this.counter;
    }
}
