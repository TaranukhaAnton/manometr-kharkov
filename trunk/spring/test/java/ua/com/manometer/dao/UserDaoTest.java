package ua.com.manometer.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ua.com.manometer.model.User;

@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
public class UserDaoTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    UserDao userDao;

    @Test
    public void testCreateUser() throws Exception {
        User user = new User();
        userDao.createUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {

    }

    @Test
    public void testGetUserById() throws Exception {

    }
}
