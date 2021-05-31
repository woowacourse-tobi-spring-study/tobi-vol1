package com.binghe.ch04.dao;

import com.binghe.ch04.domain.User;
import java.util.List;

public interface UserDao {
    void add(User user);
    User get(String id);
    List<User> getAll();
    void deleteAll();
    int getCount();
}
