package com.sp.dao;

import com.sp.model.ResellerAlternateReport;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class ResellerAlternateReportDao extends BaseDao {
    public List<ResellerAlternateReport> findByResellerId(int resellerId) {
           Criteria criteria = getSession().createCriteria(ResellerAlternateReport.class);
           criteria.add(Restrictions.eq("resellerId", resellerId));
           return criteria.list();
    }

     public ResellerAlternateReport findByResellerIdAndAlternateReportId(int resellerId, int alternateReportId) {
        Criteria criteria = getSession().createCriteria(ResellerAlternateReport.class);
        criteria.add(Restrictions.eq("resellerId", resellerId));
        criteria.add(Restrictions.eq("alternateReport.id", alternateReportId));
        return (ResellerAlternateReport) criteria.uniqueResult();
    }

}
