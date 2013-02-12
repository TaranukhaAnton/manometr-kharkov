package com.sp.dao;

import com.sp.model.ReplicatedIncomingLogRecord;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import java.util.List;

/**
 * User: andrew
 * Date: 02.07.2010
 */
public class ReplicatedIncomingLogDao extends BaseDao {
    public List<ReplicatedIncomingLogRecord> findReplicated(int maxRecordCount) {
        Query query = getSession().createQuery("from com.sp.model.ReplicatedIncomingLogRecord order by recDate, id");
        return query.setMaxResults(maxRecordCount).list();
    }

    public void deleteById(List<Integer> idList) {
        Query query = getSession().createSQLQuery("delete from replicated_incoming_log where id in (:idList)");
        query.setParameterList("idList", idList, Hibernate.INTEGER).executeUpdate();
    }
}
