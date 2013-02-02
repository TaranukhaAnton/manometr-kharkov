package ua.com.manometer.service;



import ua.com.manometer.model.User;

import java.util.List;

public interface UserService {

	public User getUser(Integer userId);

    public void addUser(User user);

	public List<User> listUser();

	public void removeUser(Integer id);

    public User findByLogin(String login);
}
