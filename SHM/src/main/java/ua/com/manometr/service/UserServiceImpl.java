package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.UserDAO;
import ua.com.manometr.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public void addUser(User user) {
		userDAO.addUser(user);
	}

	@Override
	@Transactional
	public List<User> listUser() {
		return userDAO.listUser();
	}

	@Override
	@Transactional
	public void removeUser(Long id) {
		userDAO.removeUser(id);
	}

}
