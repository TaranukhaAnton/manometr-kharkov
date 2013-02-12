package com.sp.dao;

import org.hibernate.Query;

/**
 * User: andrew
 * Date: 13.08.2010
 */
public class TotalsDao extends BaseDao {
    public void dailyTotalsCalc() {
        Query query = getSession().createSQLQuery("call daily_totals_calc()");
        query.executeUpdate();
    }
}
