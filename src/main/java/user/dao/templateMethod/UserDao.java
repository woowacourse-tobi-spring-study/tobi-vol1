package user.dao.templateMethod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import user.domain.User;

public abstract class UserDao {
    public void add(User user) throws ClassNotFoundException, SQLException {
        final Connection connection = getConnection();
        final PreparedStatement preparedStatement = connection.prepareStatement(
            "insert into users(id, name, password) values(?,?,?)"
        );
        preparedStatement.setString(1, user.getId());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        final Connection connection = getConnection();

        final PreparedStatement preparedStatement = connection.prepareStatement(
            "select * from users where id = ?"
        );
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

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
}
