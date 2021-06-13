package user.dao;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import user.CountingDaoFactory;
import user.connection.CountingConnectionMaker;
import user.domain.Level;
import user.domain.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UserDaoWithCountingTest {
    @Test
    public void addAndGet() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
        UserDaoJdbc dao = context.getBean("userDao", UserDaoJdbc.class);

        dao.deleteAll();

        final User user = new User("hello1", "hello1", "pw1", Level.BASIC, 1, 0);

        dao.add(user);

        final User daoUser = dao.get(user.getId());
        assertThat(daoUser.getId()).isEqualTo("hello1");
        assertThat(daoUser.getName()).isEqualTo("hello1");
        assertThat(daoUser.getPassword()).isEqualTo("pw1");

        CountingConnectionMaker ccm = context.getBean("connectionMaker", CountingConnectionMaker.class);
        //이제 그냥 datasource 주입받아서 사용
        assertThat(ccm.getCounter()).isEqualTo(0);
    }
}
