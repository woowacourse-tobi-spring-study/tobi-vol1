package com.binghe.user.dao;

import com.binghe.user.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.dao.EmptyResultDataAccessException;

public class UserDao {

    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {
        Connection con = connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection con = connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("select * from users where id =?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
        }

        // 리소스를 반환하는 관심
        rs.close();
        ps.close();
        con.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {
        Connection con = connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("DELETE FROM users");
        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }

    public int getCount() throws ClassNotFoundException, SQLException {
        Connection con =connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("SELECT count(*) FROM users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        // 리소스를 반환하는 관심
        rs.close();
        ps.close();
        con.close();

        return count;
    }
}