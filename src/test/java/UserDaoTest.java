import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.domain.User;
import user.domain.UserDao;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDaoTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        final String id = "JunitBean";
        final String name = "JunitBean";
        final String password = "pw";

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setPassword(password);

        dao.add(user);

        final User daoUser = dao.get(user.getId());
        assertThat(daoUser.getId()).isEqualTo(id);
        assertThat(daoUser.getName()).isEqualTo(name);
        assertThat(daoUser.getPassword()).isEqualTo(password);
    }
}
