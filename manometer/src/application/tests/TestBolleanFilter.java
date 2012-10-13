package application.tests;

import application.data.Supplier;
import application.hibernate.Factory;
import application.hibernate.generic.GenericHibernateDAO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: anton
 * Date: 26.09.12
 * Time: 7:51
 * To change this template use File | Settings | File Templates.
 */
public class TestBolleanFilter  {
    public static void main(String[] args) {
        GenericHibernateDAO<Supplier> dao = new GenericHibernateDAO<Supplier>();
        List<Supplier> def = dao.findAll();
//        List<Supplier> def = Factory.getSupplierDAO().findListByParameter("def", true);

    }
}
