package application.tests;

import application.data.price.OptionsPrice;
import application.hibernate.generic.GenericHibernateDAO;
import application.hibernate.Factory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Anton
 * Date: 03.02.2010
 * Time: 20:49:49
 * To change this template use File | Settings | File Templates.
 */
public class testOptionsPrice {
    public static void main(String[] args) {
        String[] array = {"ou0", "ou1", "ou2", "ou3", "ou4", "ou5", "du1", "du2", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14", "H15", "H16", "H17", "H18", "H19", "H20", "H21", "H22", "H31", "H32", "H33", "H34", "H35", "H36", "H37", "H38", "H39", "H40", "H41", "H42", "I", "PI", "VM", "HIM", "R", "RK", "RD", "GP"};
        GenericHibernateDAO<OptionsPrice> ddd = Factory.getOptionsPriceDAO();

        
                OptionsPrice p = new OptionsPrice();

                p.setType(0);
                p.setIsp(0);
                p.setParam("ou0"); 
      List res =  ddd.findByExample(p,new LinkedList());
        System.out.println("res.size() = " + res.size());
        
        
        int count =0;

//        for (int type = 0; type < 3; type++)
//            for (int isp = 0; isp < 5; isp++)
//            for (String param:array)
//
//            {
//                OptionsPrice p = new OptionsPrice();
//                p.setCost(new BigDecimal("0"));
//                p.setPrice(new BigDecimal("0"));
//                p.setType(type);
//                p.setIsp(isp);
//                p.setParam(param);
//                
//                ddd.makePersistent(p);
//                count++;
//            }

        System.out.println("count = " + count);


    }
}
