package ua.com.manometr.dao;

import ua.com.manometr.model.User;

import java.util.List;

public interface UserDAO {

    public void addUser(User user);

    public List<User> listUser();

    public void removeUser(Long id);

    public User getUser(Long userId);
}
