package springbook.user.dao;

public class DaoFactoryTest {
    public static void main(String[] args) {
        UserDao userDao1 = DaoFactory.userDao();
        UserDao userDao2 = DaoFactory.userDao();

        System.out.println(userDao1);
        System.out.println(userDao2);
        System.out.println(userDao1 == userDao2);
    }
}
