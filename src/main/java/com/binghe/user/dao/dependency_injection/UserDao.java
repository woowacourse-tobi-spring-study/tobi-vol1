package com.binghe.user.dao.dependency_injection;

import com.binghe.user.dao.ConnectionMaker;
import com.binghe.user.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws SQLException, ClassNotFoundException {

        // 다른 클래스로부터 커넥션을 가져온다.
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

        // 다른 클래스로부터 커넥션을 가져온다.
        Connection con = connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("select * from users where id =?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));

        // 리소스를 반환하는 관심
        rs.close();
        ps.close();
        con.close();

        return user;
    }

    public void deleteAll() throws ClassNotFoundException, SQLException {

        // 다른 클래스로부터 커넥션을 가져온다.
        Connection con = connectionMaker.getConnection();

        // SQL 실행 관심
        PreparedStatement ps = con.prepareStatement("DELETE FROM users");
        ps.executeUpdate();

        // 리소스를 반환하는 관심
        ps.close();
        con.close();
    }
}
