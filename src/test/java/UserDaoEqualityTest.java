import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import user.domain.UserDao;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserDaoEqualityTest {
    @Test
    void equalityTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDao dao1 = context.getBean("userDao", UserDao.class);
        UserDao dao2 = context.getBean("userDao", UserDao.class);
        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        assertThat(dao1).isEqualTo(dao2);
    }

    @Test
    void DirtiesTest() {
        UserDao dao1 = dirtiesUserDao();
        UserDao dao2 = dirtiesUserDao();
        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        assertThat(dao1).isNotEqualTo(dao2);
    }

    @DirtiesContext
    private UserDao dirtiesUserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        return context.getBean("userDao", UserDao.class);
    }
}
