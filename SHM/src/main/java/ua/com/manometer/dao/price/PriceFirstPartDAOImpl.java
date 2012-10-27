package ua.com.manometer.dao.price;

import org.hibernate.*;
import org.hibernate.criterion.Expression;
import ua.com.manometer.model.price.PriceFirstPart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class PriceFirstPartDAOImpl implements PriceFirstPartDAO {

    @Autowired
    private SessionFactory sessionFactory;


    public List<PriceFirstPart> getItems(List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> clime, List<Integer> isp) {
        List<PriceFirstPart> result = null;

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(PriceFirstPart.class);
        criteria.add(Expression.in("model", models));
        criteria.add(Expression.in("mat", mat));
        criteria.add(Expression.in("err", err));
        criteria.add(Expression.in("klim", clime));
        criteria.add(Expression.in("isp", isp));
        result = criteria.list();

        return result;

    }


    public void setPrice(BigDecimal cost, BigDecimal prise, List<Integer> models, List<Integer> err, List<Integer> mat, List<Integer> klim, List<Integer> isp) {


        String hqlUpdate = "update PriceFirstPart p set";
        hqlUpdate += (prise != null) ? " p.priceTmp = :price " : "";
        hqlUpdate += ((prise != null) && (cost != null)) ? " , " : "";
        hqlUpdate += (cost != null) ? " p.costTmp = :cost " : "";

        hqlUpdate += "where p.err in (:err) and p.model in (:models) and p.isp in (:isp) and p.mat in (:mat) and p.klim in (:klim)";
        // System.out.println(hqlUpdate);
        Query q = sessionFactory.getCurrentSession().createQuery(hqlUpdate)
                .setParameterList("err", err)
                .setParameterList("models", models)
                .setParameterList("isp", isp)
                .setParameterList("klim", klim)
                .setParameterList("mat", mat);
        if (prise != null) q.setBigDecimal("price", prise);
        if (cost != null) q.setBigDecimal("cost", cost);
        q.executeUpdate();

    }

    public void applyTmpValues() {
        String hqlUpdate = "update PriceFirstPart p set p.price = p.priceTmp, p.cost = p.costTmp";
        sessionFactory.getCurrentSession().createQuery(hqlUpdate).executeUpdate();
    }

    public void resetTmpValues() {
        String hqlUpdate = "update PriceFirstPart p set p.priceTmp = 0, p.costTmp = 0";
        sessionFactory.getCurrentSession().createQuery(hqlUpdate).executeUpdate();
    }

    public void priceValuesToTmp() {
        String hqlUpdate = "update PriceFirstPart p set p.priceTmp = p.price, p.costTmp = p.cost";
        sessionFactory.getCurrentSession().createQuery(hqlUpdate).executeUpdate();
    }

}
