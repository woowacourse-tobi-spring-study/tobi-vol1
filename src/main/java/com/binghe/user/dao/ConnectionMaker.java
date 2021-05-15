package com.binghe.user.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker {

    Connection getConnection() throws ClassNotFoundException, SQLException;
}
