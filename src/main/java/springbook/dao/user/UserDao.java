package springbook.dao.user;

import springbook.domain.user.User;

import java.sql.*;

public class UserDao {

    public void addUser(User user) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (?, ?, ?)");
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:13306/tobi", "root", "root");
    }

    public User getUser(String id) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();
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
