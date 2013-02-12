package com.sp.dao;

import com.sp.model.AddressgeoMap;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class AddressgeoMapDao extends BaseDao {
    public List<AddressgeoMap> findByText(String text) {
        Criteria criteria = getSession().createCriteria(AddressgeoMap.class);
        criteria.add(Restrictions.like("postcode", "%" + text + "%"));
        criteria.setMaxResults(20);

        return criteria.list();
    }
}
