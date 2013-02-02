package ua.com.manometer.dao;

import ua.com.manometer.model.User;

import java.util.List;

public interface UserDAO {

    public void addUser(User user);

    public List<User> listUser();

    public void removeUser(Integer id);

    public User getUser(Integer userId);


    public User findByLogin(String login);

}
