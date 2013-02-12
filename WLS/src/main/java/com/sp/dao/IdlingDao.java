package com.sp.dao;

import com.sp.model.IdlingLog;
import com.sp.util.TimePeriod;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;


public class IdlingDao extends BaseDao {

    public List<IdlingLog> fingIdlingLogByPeriodAndUnitId(TimePeriod timePeriod,
                                                          List<Integer> unitIdList) {

        Session session = getSession();
        String queryText = "select {i.*} FROM idling_log {i} " +
                " where i.start_date >= ? and i.start_date <= ? " +
                " and i.end_date >= ? and i.end_date <= ? " +
                " and i.unit_id in (:unitIdList)" +
                " order by i.unit_id, i.start_date";
        Query query = session.createSQLQuery(queryText)
                 .addEntity("i", IdlingLog.class)
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setTimestamp(2, timePeriod.getStartDate())
                .setTimestamp(3, timePeriod.getEndDate());
        query.setParameterList("unitIdList", unitIdList);
        return query.list();
    }


}
