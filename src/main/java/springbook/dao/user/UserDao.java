package springbook.dao.user;

import springbook.dao.connection.ConnectionMaker;
import springbook.domain.user.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private ConnectionMaker connectionMaker;

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void addUser(User user) throws ClassNotFoundException, SQLException {
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (?, ?, ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public User getUser(String id) throws SQLException, ClassNotFoundException {
        Connection connection = connectionMaker.makeConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getString("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }
}
