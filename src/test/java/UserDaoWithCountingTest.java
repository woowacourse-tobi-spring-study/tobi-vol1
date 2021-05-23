import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.connection.CountingConnectionMaker;
import user.domain.User;
import user.dao.UserDao;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDaoWithCountingTest {
    @Test
    public void addAndGet() throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDao dao = context.getBean("userDao", UserDao.class);

        final String id = "JunitCount";
        final String name = "JunitCount";
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

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        assertThat(ccm.getCounter()).isEqualTo(2);
    }
}
