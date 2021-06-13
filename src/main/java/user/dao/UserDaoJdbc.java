package user.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;

    public UserDaoJdbc(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<User> userMapper = new RowMapper<User>() {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            final User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            user.setLevel(rs.getInt("level"));
            user.setLogin(rs.getInt("login"));
            user.setRecommend(rs.getInt("recommend"));
            return user;
        }
    };

    @Override
    public void add(User user) {
        this.jdbcTemplate.update("insert into users (id, name, password, level, login, recommend) values (?, ?, ?, ?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend());
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?", new Object[]{id}, userMapper);
    }

    @Override
    public List<User> getAll() {
        final String sql = "select * from users";
        return jdbcTemplate.query(sql, userMapper);
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.queryForObject("select count (*) from users", Integer.class);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }
}

/*
getConnection() 메서드는 어떤 Connection 클래스의 오브젝트를 어떻게 생성할 지를 결정하는 방법
서브 클래스에서 구체적인 오브젝트 생성 방법을 결정하게 하는 것을 "팩토리 메서드 패턴"이라고 한다

주로 인터페이스 타입으로 오브젝트를 리턴하기에, 서브클래스에서 정확히 어떤 클래스의 오브젝트 만들어 리턴할 지 슈퍼클래스는 모름
+ 관심도 없음

서브 클래스에서 오브젝트 생성 방법과 클래스를 결정할 수 있도록 미리 정의해둔 메서드를 팩토리 메서드라고 함
*/