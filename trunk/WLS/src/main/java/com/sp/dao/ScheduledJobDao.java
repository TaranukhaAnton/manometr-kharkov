package com.sp.dao;

import com.sp.model.ScheduledJob;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class ScheduledJobDao extends BaseDao {

    public ScheduledJob findNextJobToExecute() {
        Session session = getSession();
        Query query = session.createSQLQuery(
                        "SELECT {j.*} FROM " +
                                "(SELECT * FROM scheduled_job" +
                                " WHERE j.type != 'running'" +
                                " ORDER BY next_start_date) {j}" +
                                " limit 1").addEntity("j", ScheduledJob.class);

        return (ScheduledJob) query.uniqueResult();
    }

    public List<ScheduledJob> findForUserId(int userId) {
        Session session = getSession();
        Query query = session.createQuery("from com.sp.model.ScheduledJob where securityUser.id = ?").setInteger(0, userId);

        return query.list();
    }
}
