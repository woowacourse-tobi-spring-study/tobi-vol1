package springbook.service;

import org.springframework.stereotype.Service;
import springbook.dao.UserDao;

@Service
public class UserService {

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
