package com.sp.dao;

import com.sp.model.DallasKey;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class DallasKeyDao extends BaseDao {
    public List<DallasKey> findAllUnassigned() {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {dk.*} " +
                " FROM dallas_key {dk} " +
                " WHERE id NOT IN (SELECT dallas_key_id FROM security_user WHERE dallas_key_id IS NOT NULL) and account_id IS NOT NULL").addEntity("dk", DallasKey.class);

        return query.list();
    }

     public List<DallasKey> findAllForUser(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {dk.*} " +
                " FROM dallas_key {dk} " +
                " WHERE account_id IN (SELECT account_id FROM map_user_account mua " +
                " JOIN security_user su ON su.id = mua.user_id WHERE su.id=?)")
                .addEntity("dk", DallasKey.class).setInteger(0, userId);

        return query.list();
    }

     public List<DallasKey> findAllUnassignedForUser(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {dk.*} " +
                " FROM dallas_key {dk} " +
                " WHERE account_id IN (SELECT account_id FROM map_user_account mua " +
                " JOIN security_user su ON su.id = mua.user_id WHERE su.id=?) AND id" +
                 " NOT IN (SELECT dallas_key_id FROM security_user WHERE dallas_key_id IS NOT NULL) and account_id IS NOT NULL")
                .addEntity("dk", DallasKey.class).setInteger(0, userId);

        return query.list();
    }

    public boolean isDallasKeyWithCodeExists(String dallasCode){
        Criteria criteria = getSession().createCriteria(DallasKey.class);
        criteria.add(Restrictions.eq("dallasKeyCode", dallasCode));
        DallasKey dallasKey = (DallasKey)criteria.uniqueResult();
        return dallasKey!=null;
    }
}
