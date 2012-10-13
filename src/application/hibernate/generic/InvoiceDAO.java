package application.hibernate.generic;

import application.data.Customer;
import application.data.invoice.Invoice;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 10.04.2010
 * Time: 12:22:26
 * To change this template use File | Settings | File Templates.
 */
public class InvoiceDAO extends GenericHibernateDAO<Invoice> {

        public void setPrice(BigDecimal cost, BigDecimal prise, List<Long> models, List<Integer> err, List<Integer> mat, List<Integer> klim, List<Integer> isp) {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String hqlUpdate = "update PriceFirstPart p set";
            hqlUpdate += (prise != null) ? " p.priceTmp = :price " : "";
            hqlUpdate += ((prise != null) && (cost != null)) ? " , " : "";
            hqlUpdate += (cost != null) ? " p.costTmp = :cost " : "";

            hqlUpdate += "where p.err in (:err) and p.model in (:models) and p.isp in (:isp) and p.mat in (:mat) and p.klim in (:klim)";
            // System.out.println(hqlUpdate);
            Query q = session.createQuery(hqlUpdate)
                    .setParameterList("err", err)
                    .setParameterList("models", models)
                    .setParameterList("isp", isp)
                    .setParameterList("klim", klim)
                    .setParameterList("mat", mat);
            if (prise != null) q.setBigDecimal("price", prise);
            if (cost != null) q.setBigDecimal("cost", cost);
            q.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx != null) tx.commit();


         
        }
    }
}
