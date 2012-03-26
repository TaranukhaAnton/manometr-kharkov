package ua.com.manometer.service;



import ua.com.manometer.model.User;

import java.util.List;

public interface UserService {

	public User getUser(Long userId);

    public void addUser(User user);

	public List<User> listUser();

	public void removeUser(Long id);

}
