package springbook.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import springbook.dao.strategy.StatementStrategy;
import springbook.domain.user.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private JdbcContext jdbcContext;
    private DataSource dataSource;

    public void addUser(User user) {
        jdbcContext.workWithStatementStrategy(new StatementStrategy() {
            @Override
            public PreparedStatement makeStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO USERS (ID, NAME, PASSWORD) VALUES (?, ?, ?)");
                preparedStatement.setString(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getPassword());
                return preparedStatement;
            }
        });
    }

    public User getUser(String id) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE ID = ?");
        preparedStatement.setString(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        User user = null;
        if (resultSet.next()) {
            user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
        }
        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }

    public void deleteAll() {
        jdbcContext.executeSql("delete from users");
    }

    public int getCount() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from users");
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt(1);
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return count;
    }

    public void setJdbcContext(JdbcContext jdbcContext) {
        this.jdbcContext = jdbcContext;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
