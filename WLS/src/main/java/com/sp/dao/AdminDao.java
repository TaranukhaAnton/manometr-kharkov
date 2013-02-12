package com.sp.dao;

import org.hibernate.Query;
import org.hibernate.Session;

public class AdminDao extends BaseDao {

    public void killDatabaseProcess(int processId) {
        Session session = getSession();
        Query query = session.createSQLQuery("kill ?");
        query.setInteger(0, processId).executeUpdate();
    }

}
