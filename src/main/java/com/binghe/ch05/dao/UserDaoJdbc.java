package com.binghe.ch05.dao;

import com.binghe.ch05.domain.Level;
import com.binghe.ch05.domain.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDaoJdbc implements UserDao {

    private static final RowMapper<User> mapper = (rs, rowNum) -> {
        String id = rs.getString("id");
        String name = rs.getString("name");
        String password = rs.getString("password");
        String email = rs.getString("email");
        Level level = Level.valueOf(rs.getInt("level"));
        int login = rs.getInt("login");
        int recommend = rs.getInt("recommend");
        return new User(id, name, password, email, level, login, recommend);
    };

    private JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(User user) {
        String sql = "INSERT INTO users(id, name, password, email, level, login, recommend) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            user.getId(), user.getName(), user.getPassword(), user.getEmail(),
            user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    @Override
    public User get(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, mapper, id);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT * FROM users ORDER BY id";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public void deleteAll() {
        String sql = "DELETE FROM users";
        jdbcTemplate.update(sql);
    }

    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET name = ?, password = ?, email = ?, level = ?, login = ?, recommend = ? WHERE id = ?";
        jdbcTemplate.update(sql,
            user.getName(), user.getPassword(), user.getEmail(), user.getLevel().intValue(),
            user.getLogin(), user.getRecommend(), user.getId());
    }
}
