import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import user.dao.UserDao;
import user.domain.User;

import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DaoFactoryForTest.class)
public class UserDaoTest {

    @Autowired
    private UserDao dao;

    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
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
        dao.deleteAll();
        final User user1 = new User("hello1", "world1", "pw1");
        final User user2 = new User("hello2", "world2", "pw2");
        final User user3 = new User("hello3", "world3", "pw3");

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        assertThat(dao.getCount()).isEqualTo(3);
    }

    @Test
    public void getAll() {
        dao.deleteAll();
        final User user1 = new User("hello1", "world1", "pw1");
        final User user2 = new User("hello2", "world2", "pw2");
        final User user3 = new User("hello3", "world3", "pw3");

        dao.add(user1);
        dao.add(user2);
        dao.add(user3);

        final List<User> users = dao.getAll();
        System.out.println("users = " + users);
        assertThat(users.size()).isEqualTo(3);
    }
}
