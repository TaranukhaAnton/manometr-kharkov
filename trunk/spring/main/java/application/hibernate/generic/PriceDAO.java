package application.hibernate.generic;


import ua.com.manometer.model.price.PriceFirstPart;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import java.math.BigDecimal;
import java.util.List;


public class PriceDAO extends GenericHibernateDAO<PriceFirstPart> {


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


//    public PriceFirstPart getPrice(Long model, Integer err, Integer mat, Integer klim, Integer isp) {
//        Session session = null;
//        Transaction tx = null;
//        PriceFirstPart result = null;
//        try {
//            session = getSession();
//            tx = session.beginTransaction();
//            Criteria crit = session.createCriteria(getPersistentClass());
//            crit.add(Example.create(new PriceFirstPart(model, isp, mat, klim, err)));
//            result = (PriceFirstPart) crit.uniqueResult();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (tx != null) tx.commit();
//
//
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
//         return result;
//
//
//    }


    public List<PriceFirstPart> getItems(List<Long> models, List<Integer> err, List<Integer> mat, List<Integer> klim, List<Integer> isp) {
        List<PriceFirstPart> result = null;
//        Long[] model = {3030l};
//        Integer[] isp = {2};
//        Integer[] err = {3};
//        Integer[] mat = {0};
//        Integer[] klim = {0};

        // String query = "select p  from PriceFirstPart p where p.err=3 and p.model in (:models) and p.isp = 2 and p.mat =0 and p.klim =0";

        Session session = null;

        try {
            session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            crit.add(Expression.in("model", models));
            crit.add(Expression.in("mat", mat));
            crit.add(Expression.in("err", err));
            crit.add(Expression.in("klim", klim));
            crit.add(Expression.in("isp", isp));
            result = crit.list();


//            Criteria crit = session.createCriteria(getPersistentClass());
//
//            crit.add(Expression.in("model", model));
//            crit.add(Expression.in("mat", mat));
//            crit.add(Expression.in("err", err));
//            crit.add(Expression.in("klim", klim));
//            crit.add(Expression.in("isp", isp));
//
//
//            result = crit.list();
//                 Query q = session.createQuery(query)
//                  //  .setParameterList("err", err)
//                    .setParameterList("models", model);
////                    .setParameterList("isp", isp)
////                    .setParameterList("klim", klim);
//                    //.setParameterList("mat", mat);
//          result=  q.list();


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }


    public void applayTmpValues()

    {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String hqlUpdate = "update PriceFirstPart p set p.price = p.priceTmp, p.cost = p.costTmp";

            session.createQuery(hqlUpdate).executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx != null) tx.commit();
        }
    }

    public void resetTmpValues() {
        Session session = null;
        Transaction tx = null;
        try {
            session = getSession();
            tx = session.beginTransaction();

            String hqlUpdate = "update PriceFirstPart p set p.priceTmp = 0, p.costTmp = 0";

            session.createQuery(hqlUpdate).executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (tx != null) tx.commit();


        }
    }
}
