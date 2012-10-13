package application.tests;

import application.data.User;
import application.hibernate.Factory;

/**
 * Created by IntelliJ IDEA.
 * User: Anton
 * Date: 26.01.2010
 * Time: 0:07:15
 * To change this template use File | Settings | File Templates.
 */
public class testProdList {
    public static void main(String[] args) {
        User u = Factory.getUserDAO().findById(1L);


    }
}
