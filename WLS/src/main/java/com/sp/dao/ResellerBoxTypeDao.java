package com.sp.dao;

import com.sp.model.ResellerBoxType;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * User: andrew
 * Date: 21.06.2010
 */
public class ResellerBoxTypeDao extends BaseDao {
    public List<ResellerBoxType> findByResellerId(int resellerId) {
        Criteria criteria = getSession().createCriteria(ResellerBoxType.class);
        criteria.add(Restrictions.eq("resellerId", resellerId));
        return criteria.list();
    }

    public ResellerBoxType findByResellerIdAndBoxTypeId(int resellerId, int boxTypeId) {
        Criteria criteria = getSession().createCriteria(ResellerBoxType.class);
        criteria.add(Restrictions.eq("resellerId", resellerId));
        criteria.add(Restrictions.eq("boxType.id", boxTypeId));
        return (ResellerBoxType) criteria.uniqueResult();
    }
}
