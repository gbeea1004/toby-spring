package geon.hee.tobyspring.repository;

import geon.hee.tobyspring.domain.User;

import java.util.List;

public interface UserDao {

    void add(User user);

    User get(String id);

    void deleteAll();

    int getCount();

    List<User> getAll();

    void update(User user);
}
