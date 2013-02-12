package com.sp.dao;

import com.sp.model.CollectorLog;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import java.text.ParseException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class CollectorLogDao extends BaseDao {
    public List<CollectorLog> findByPeriod(TimePeriod timePeriod, String filter) throws ParseException {
        Criteria criteria = getSession().createCriteria(CollectorLog.class);
        criteria.add(Restrictions.between("createdDate", timePeriod.getStartDate(), timePeriod.getEndDate()));
        if (filter != null && filter.trim().length() > 0) {
            criteria.add(Restrictions.like("descr", filter, MatchMode.ANYWHERE));
        }

        return criteria.list();
    }
}
