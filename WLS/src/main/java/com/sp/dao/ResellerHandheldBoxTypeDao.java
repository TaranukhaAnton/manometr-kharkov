package com.sp.dao;

import com.sp.model.ResellerHandheldBoxType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;


public class ResellerHandheldBoxTypeDao extends BaseDao {

    public List<ResellerHandheldBoxType> findByResellerId(int resellerId) {
        Criteria criteria = getSession().createCriteria(ResellerHandheldBoxType.class);
        criteria.add(Restrictions.eq("resellerId", resellerId));
        return criteria.list();
    }

    public ResellerHandheldBoxType findByResellerIdAndBoxTypeId(int resellerId, int boxTypeId) {
        Criteria criteria = getSession().createCriteria(ResellerHandheldBoxType.class);
        criteria.add(Restrictions.eq("resellerId", resellerId));
        criteria.add(Restrictions.eq("handheldBoxType.id", boxTypeId));
        return (ResellerHandheldBoxType) criteria.uniqueResult();
    }
}
