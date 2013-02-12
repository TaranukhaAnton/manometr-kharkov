package com.sp.dao;

import com.sp.model.RawIncomingLogRecord;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: andrew
 * Date: 07.09.2010
 */
public class RawIncomingLogDao extends BaseDao {
    public List<RawIncomingLogRecord> findByPeriodAndImei(TimePeriod period, String imei) {
        Criteria criteria = getSession().createCriteria(RawIncomingLogRecord.class);
        criteria.add(Restrictions.between("timestamp", period.getStartDate(), period.getEndDate()));
        criteria.add(Restrictions.eq("imei", imei));
        criteria.addOrder(Order.asc("timestamp"));

        return criteria.list();
    }
}
