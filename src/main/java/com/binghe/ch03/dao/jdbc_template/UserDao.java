package com.binghe.ch03.dao.jdbc_template;

import com.binghe.ch03.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class UserDao {
    private static final RowMapper<User> userRowMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            return user;
        }
    };

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteAll() {
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(
                Connection connection) throws SQLException {
                return connection.prepareStatement("delete from users");
            }
        });

        // 전에 만들었던 executeSql처럼 SQL만으로 동작하도록 JDBCTemplate에서 제공한다.
        // jdbcTemplate.update("delete from users");
    }

    public void add(User user) {
        jdbcTemplate.update("insert into users(id, name, password) values(?, ?, ?)", user.getId(), user.getName(), user.getPassword());
    }

    public int getCount() {
        return jdbcTemplate.query("select count(*) from users", new ResultSetExtractor<Integer>() {
            @Override
            public Integer extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                resultSet.next();
                return resultSet.getInt(1);
            }
        });
    }

    public User get(String id) {
//         return jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, userRowMapper);
        return this.jdbcTemplate.queryForObject("select * from users where id = ? ", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setId(resultSet.getString("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                return user;
            }
        }, id);
    }

    public List<User> getAll(){
        // return jdbcTemplate.query("select * from users order by id", userRowMapper);
        return jdbcTemplate.query("select * from users order by id",
            new RowMapper<User>() {
                @Override
                public User mapRow(ResultSet resultSet, int i) throws SQLException {
                    User user = new User();
                    user.setId(resultSet.getString("id"));
                    user.setName(resultSet.getString("name"));
                    user.setPassword(resultSet.getString("password"));
                    return user;
                }
            });
    }
}
