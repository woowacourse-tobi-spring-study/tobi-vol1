package springbook.dao;

import springbook.domain.user.User;

import java.util.List;

public interface UserDao {

    public void addUser(User user);

    public User getUser(String id);

    public void deleteAll();

    public int getCount();

    public List<User> getAll();
}
