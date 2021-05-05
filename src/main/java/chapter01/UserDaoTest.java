package chapter01;

import chapter01.dao.UserDao;

public class UserDaoTest {
    public static void main(String[] args) {
        UserDao userDao = new DaoFactory().userDao();
    }
}
