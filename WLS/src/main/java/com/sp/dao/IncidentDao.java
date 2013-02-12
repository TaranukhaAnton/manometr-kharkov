package com.sp.dao;

import com.sp.model.Incident;
import com.sp.util.Constants;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;


/**
 * Created by Alexander
 */
public class IncidentDao extends BaseDao {
    // can be removed in the future
    public List<Incident> findIncidentList(Constants.IncidentType incidentType, Date timestamp){
        Criteria criteria = getSession().createCriteria(Incident.class);
        criteria.add(Restrictions.eq("type", incidentType.toString()));
        criteria.add(Restrictions.gt("timestamp", timestamp));
        return criteria.list();
    }
}
