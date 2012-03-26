package ua.com.manometer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.UserDAO;
import ua.com.manometer.model.User;
import ua.com.manometer.model.invoice.InvoiceFilter;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User getUser(Long userId) {
        return userDAO.getUser(userId);
    }

    @Override
    @Transactional
    public void addUser(User user) {
        if (user.getId() == null) {
            user.setInvoiceFilter(new InvoiceFilter());
        } else {
            final InvoiceFilter invoiceFilter = userDAO.getUser(user.getId()).getInvoiceFilter();
            user.setInvoiceFilter(invoiceFilter);
        }
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
