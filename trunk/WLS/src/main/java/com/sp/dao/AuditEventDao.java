package com.sp.dao;

import com.sp.model.AuditEvent;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class AuditEventDao extends BaseDao {
    public List<AuditEvent> findByPeriod(TimePeriod timePeriod) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(AuditEvent.class);
        criteria.add(Restrictions.between("createdDate", timePeriod.getStartDate(), timePeriod.getEndDate()));

        criteria.addOrder(Order.asc("createdDate"));

        return (List<AuditEvent>) criteria.list();
    }
}