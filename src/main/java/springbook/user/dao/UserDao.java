package springbook.user.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private JdbcContext jdbcContext;
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.jdbcContext = new JdbcContext(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) throws SQLException {
        String sql = "INSERT INTO users (id, name, password) VALUES (?,?,?)";

        jdbcContext.workWithStatementStrategy(con -> {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getPassword());
            return preparedStatement;
        });
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id = ?");
        preparedStatement.setString(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return user;
    }

    public int getCount() throws SQLException {
        Connection connection = dataSource.getConnection();
        String sql = "SELECT COUNT(*) FROM users";
        int count;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            rs.next();
            count = rs.getInt(1);
        }

        return count;
    }

    public List<User> getAll() throws ClassNotFoundException, SQLException {
        Connection connection = dataSource.getConnection();

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

    public void deleteAll() throws SQLException {
        jdbcContext.executeSql("DELETE FROM users");
    }


}
