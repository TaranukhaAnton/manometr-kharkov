package ua.com.manometr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.UserDAO;
import ua.com.manometr.model.User;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public ua.com.manometr.webbeans.User getUser(Long userId) {
        User user = userDAO.getUser(userId);
        return new ua.com.manometr.webbeans.User(user);
    }

    @Override
    @Transactional
    public void addUser(ua.com.manometr.webbeans.User webUser) {
        User user;
        if (webUser.getId() == null) {
            user = new User();
        } else {
            user = userDAO.getUser(webUser.getId());
       }
        try {
            webUser.toDBUser(user);
        } catch (ParseException e) {
            // log.error(e, e); //Handle exception here!
            //todo
        }

        userDAO.addUser(user);
    }

    @Override
    @Transactional
    public List<ua.com.manometr.webbeans.User> listUser() {
        List<User> userList = userDAO.listUser();
        List<ua.com.manometr.webbeans.User> users = new LinkedList<ua.com.manometr.webbeans.User>();
        for (User user : userList) {
            users.add(new ua.com.manometr.webbeans.User(user));
        }
        return users;
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        userDAO.removeUser(id);
    }

}
