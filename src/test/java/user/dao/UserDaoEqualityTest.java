package user.dao;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import user.DaoFactory;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class UserDaoEqualityTest {
    @Test
    void equalityTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        UserDaoJdbc dao1 = context.getBean("userDao", UserDaoJdbc.class);
        UserDaoJdbc dao2 = context.getBean("userDao", UserDaoJdbc.class);
        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        assertThat(dao1).isEqualTo(dao2);
    }

    @Test
    void DirtiesTest() {
        UserDaoJdbc dao1 = dirtiesUserDao();
        UserDaoJdbc dao2 = dirtiesUserDao();
        System.out.println("dao1 = " + dao1);
        System.out.println("dao2 = " + dao2);
        assertThat(dao1).isNotEqualTo(dao2);
    }

    @DirtiesContext
    private UserDaoJdbc dirtiesUserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        return context.getBean("userDao", UserDaoJdbc.class);
    }
}
