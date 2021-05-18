package com.binghe.ch03.dao.template_method_pattern;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDaoDeleteAll extends SuperUserDao {

    @Override
    public PreparedStatement makeStatement(Connection con) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from users");
        return ps;
    }
}
