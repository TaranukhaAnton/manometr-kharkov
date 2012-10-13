package application.tests;

import application.data.SupTest;
import application.data.Supplier;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 01.08.12
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
public class SupTestWWWWWWW {

    public static void main(String[] args) {
        List<Supplier> def = Factory.getSupplierDAO().findListByParameter("firstRow", true);
        System.out.println("def.size() = " + def.size());
    }
}
