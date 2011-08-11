package ua.com.manometer.model.invoice;

import application.hibernate.HibernateSessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigDecimal;


/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 31.10.2010
 * Time: 0:00:41
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceUtil {

    public static void main(String[] args) {
        Session session = HibernateSessionFactory.getSession();

        String queryStr = "select sum( II.sellingPrice * II.quantity  ) from InvoiceItem  II";
        Query query = session.createSQLQuery(queryStr);
        Object o = new BigDecimal((Double) query.uniqueResult());
        System.out.println(o);






    }
}
