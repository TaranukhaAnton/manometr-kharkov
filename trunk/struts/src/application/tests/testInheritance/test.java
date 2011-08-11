package application.tests.testInheritance;

import application.hibernate.generic.GenericHibernateDAO;

/**
 * Created by IntelliJ IDEA.
 * User: Anton
 * Date: 11.02.2010
 * Time: 18:09:55
 * To change this template use File | Settings | File Templates.
 */
public class test {
    public static void main(String[] args) {
        GenericHibernateDAO<Project> dao = new GenericHibernateDAO<Project>() {
        };

        // LargeProject p = new LargeProject();
        SmallProject p = new SmallProject();
        // p.setId(55l);
        dao.makePersistent(p);


        GenericHibernateDAO<Item> Itemdao = new GenericHibernateDAO<Item>() {
        };

        // LargeProject p = new LargeProject();
        Item i = new Item();
        i.setProject(p);
        Itemdao.makePersistent(i);

//        p.setBaseProperty("base prop");
//        p.setSub2Property("Sub2Property");
//       p.setJoinedProperty("sdfsd");

//        Item i = new Item();
//        i.setName("name");
//     //   i.setProject(p);
//
//       dao.makePersistent(i);
    }
}
