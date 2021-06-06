package com.binghe.ch05.dao;

import com.binghe.ch05.domain.User;
import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
    void update(User user);
}
