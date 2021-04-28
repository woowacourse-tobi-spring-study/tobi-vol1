package com.binghe.user.dao.dependency_injection;

import com.binghe.user.dao.ConnectionMaker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB 연결에 대한 전략 (핵심 로직) - H2
 */
public class HConnectionMaker implements ConnectionMaker {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // DB 연결 관심
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/toby", "sa", "");
        return con;
    }
}
