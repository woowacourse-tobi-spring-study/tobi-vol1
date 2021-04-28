package com.binghe.user.dao.template_method_pattern;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * H2 DB를 연결하는 UserDao
 */
public class HUserDao extends AbstractUserDao {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        Connection con = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/toby", "sa", "");
        return con;
    }
}
