package com.binghe.user.dao.dependency_injection;

import com.binghe.user.dao.ConnectionMaker;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * DB 연결에 대한 전략 (핵심 로직) - MySQL
 */
public class MConnectionMaker implements ConnectionMaker {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // MySQL에 연결하는 로직..
        return null;
    }
}
