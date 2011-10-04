package ua.com.manometr.service;

import ua.com.manometr.model.User;

import java.util.List;

public interface UserService {

	public void addUser(User user);

	public List<User> listUser();

	public void removeUser(Long id);

}
