package springbook.user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {
    private static final RowMapper<User> USER_ROW_MAPPER = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        return user;
    };
    private JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void add(final User user) {
        String sql = "INSERT INTO users (id, name, password, level, login, recommend) VALUES (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, user.getId(),
                user.getName(), user.getPassword(),
                user.getLevel().getValue(),
                user.getLogin(), user.getRecommend());
    }

    @Override
    public User get(String id) {
        return jdbcTemplate.queryForObject("select * from users where id = ?",
                USER_ROW_MAPPER, id
        );
    }

    @Override
    public int getCount() {
        String sql = "SELECT COUNT(*) FROM users";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users",
                USER_ROW_MAPPER
        );
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("DELETE FROM users");
    }

    @Override
    public void update(User user) {
        String sql = "update USERS set name = ?, password = ?, level = ?, login = ?, " +
                "recommend = ? where id = ? ";
        this.jdbcTemplate.update(sql, user.getName(), user.getPassword(),
                user.getLevel().getValue(),
                user.getLogin(), user.getRecommend(),
                user.getId());
    }
}
