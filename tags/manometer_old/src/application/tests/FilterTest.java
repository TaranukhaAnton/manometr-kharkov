package application.tests;

import application.data.User;
import application.data.invoice.Invoice;
import application.hibernate.Factory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 06.07.2010
 * Time: 17:02:58
 * To change this template use File | Settings | File Templates.
 */
public class FilterTest {

    public static void main(String[] args) throws ParseException {

     SimpleDateFormat sdf =   new SimpleDateFormat("dd.MM.yyyy");
        Map<String,Object> eq = new HashMap();
        Map<String,Object> larger = new HashMap();
        Map<String,Object> less = new HashMap();
        Map<String,List> inList = new HashMap();

//    larger.put("date",sdf.parse("08.07.2010"));
       // eq.put("yy",1L);

        List<User> l = new LinkedList();
            l.add(Factory.getUserDAO().findById(1l));
            l.add(Factory.getUserDAO().findById(2l));
            l.add(Factory.getUserDAO().findById(3l));
//            l.add(2l);
//            l.add(6l);
         inList.put("executor",l);

        List<Invoice> result = Factory.getInvoiceDAO().findByQuery(eq,larger,less,inList);

        for (int i = 0; i < result.size(); i++) {
            Invoice o =  result.get(i);
            System.out.println(o.getId());
        }

        
    }
}
