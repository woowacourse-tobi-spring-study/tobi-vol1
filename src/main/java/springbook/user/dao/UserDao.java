package springbook.user.dao;

import springbook.user.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final SimpleConnectionMaker simpleConnectionMaker;

    public UserDao(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection connection = simpleConnectionMaker.makeNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (id, name, password) VALUES (?,?,?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.execute();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = simpleConnectionMaker.makeNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
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

    public List<User> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = simpleConnectionMaker.makeNewConnection();

        ArrayList<User> users = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));

                users.add(user);
            }
        }
        return users;
    }

    public void delete() throws SQLException, ClassNotFoundException {
        Connection connection = simpleConnectionMaker.makeNewConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users");
        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }
}
