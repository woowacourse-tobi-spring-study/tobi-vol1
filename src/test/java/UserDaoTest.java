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

        dao.deleteAll();
        assertThat(dao.getCount()).isEqualTo(0);

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

        assertThat(dao.getCount()).isEqualTo(1);
    }

    @Test
    public void getCount() throws SQLException, ClassNotFoundException {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        dao.deleteAll();
        final User user1 = new User("hello1", "world1", "pw1");
        final User user2 = new User("hello2", "world2", "pw2");
        final User user3 = new User("hello3", "world3", "pw3");

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        assertThat(dao.getCount()).isEqualTo(3);
    }
}
