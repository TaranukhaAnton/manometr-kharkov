package com.sp.dao;

import com.sp.model.LateStartPeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class LateStartPeriodDao extends BaseDao {
    public List<LateStartPeriod> findByUserId(int userId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(LateStartPeriod.class);
        criteria.add(Restrictions.eq("userId", userId));
        criteria.addOrder(Order.asc("startTimeHours"));
        criteria.addOrder(Order.asc("startTimeMins"));

        return criteria.list();
    }

    public LateStartPeriod findIntersectedPeriod(int userId, LateStartPeriod newObj) {
        Session session = getSession();
        Query query = session.createQuery("from com.sp.model.LateStartPeriod " +
                "where (((startTimeHours * 60 + startTimeMins) between ? and ?) " +
                        " or ((endTimeHours * 60 + endTimeMins) between ? and ?)" +
                        " or (? between (startTimeHours * 60 + startTimeMins) and (endTimeHours * 60 + endTimeMins))" +
                        " or (? between (startTimeHours * 60 + startTimeMins) and (endTimeHours * 60 + endTimeMins)))" +
                " and id != ? and userId = ?");

        List list = query
                .setInteger(0, newObj.getStartTimeHours() * 60 + newObj.getStartTimeMins())
                .setInteger(1, newObj.getEndTimeHours() * 60 + newObj.getEndTimeMins())
                .setInteger(2, newObj.getStartTimeHours() * 60 + newObj.getStartTimeMins())
                .setInteger(3, newObj.getEndTimeHours() * 60 + newObj.getEndTimeMins())
                .setInteger(4, newObj.getStartTimeHours() * 60 + newObj.getStartTimeMins())
                .setInteger(5, newObj.getEndTimeHours() * 60 + newObj.getEndTimeMins())
                .setInteger(6, newObj.getId())
                .setInteger(7, userId)
                .list();
        return list.size() > 0 ? (LateStartPeriod) list.get(0) : null;
    }
}
