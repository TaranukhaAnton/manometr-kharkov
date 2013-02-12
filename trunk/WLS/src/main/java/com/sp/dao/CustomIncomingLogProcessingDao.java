package com.sp.dao;

import org.hibernate.Query;

import java.util.List;

/**
 * User: andrew
 * Date: 18.08.2010
 */
public class CustomIncomingLogProcessingDao extends BaseDao {
    public List<Integer> findIds(int limit) {
        Query query = getSession().createSQLQuery(
                "SELECT cp.id" +
                " FROM custom_incoming_log_processing cp " +
                "  JOIN incoming_log l ON l.id = cp.id" +
                " ORDER BY l.NodeId, l.rec_date" + 
                " LIMIT ?")
                .setInteger(0, limit);
        return query.list();
    }

    public void deleteByIdList(List<Integer> idList) {
        getSession().createSQLQuery("DELETE FROM custom_incoming_log_processing WHERE id IN (:ids)")
                .setParameterList("ids", idList).executeUpdate();
    }
}
