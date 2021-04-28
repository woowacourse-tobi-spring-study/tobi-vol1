package com.binghe.user.dao.template_method_pattern;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * MySQL에 연결하는 UserDao
 */
public class MUserDao extends AbstractUserDao {

    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        // MySQL에 연결하는 로직..
        return null;
    }
}
