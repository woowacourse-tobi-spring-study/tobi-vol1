package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

public class UserDao {
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };
    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) {
        String sql = "INSERT INTO users (id, name, password) VALUES (?,?,?)";
        jdbcTemplate.update(sql, user.getId(), user.getName(), user.getPassword());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                USER_ROW_MAPPER, id
        );
    }

    public int getCount() {
        String sql = "SELECT COUNT(*) FROM users";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public List<User> getAll() {
        return jdbcTemplate.query("select * from users",
                USER_ROW_MAPPER
        );
    }

    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }
}
