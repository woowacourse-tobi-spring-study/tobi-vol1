package chapter01.dao;

import chapter01.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public Connections connections;

    public UserDao(Connections connections) {
        this.connections = connections;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connections.makeConnection();
        PreparedStatement ps = connection.prepareStatement(
                "insert into users(id, name, password) values(?, ?, ?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = connections.makeConnection();
        PreparedStatement ps = connection.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1, id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        User user = new User(rs.getString("id"), rs.getString("name"), rs.getString("password"));

        rs.close();
        ps.close();
        connection.close();

        return user;
    }
}
