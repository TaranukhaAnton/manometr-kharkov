package com.sp.dao;

import com.sp.model.License;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class LicenseDao extends BaseDao {
    public List<License> findLicenseByAccountId(int accountId) {
        Session session = getSession();
        return (List<License>) session.createQuery("from com.sp.model.License  as obj where obj.account.id = ?")
                .setInteger(0, accountId).list();
    }
    
    public List<License> findLicenseByAccountList(List<Integer> accountIds) {
        Session session = getSession();
        Query query = session.createQuery("from com.sp.model.License  as obj where obj.account.id in (:accountList)");
        query.setParameterList("accountList",accountIds);
        return query.list();
    }

    public List<License> findNonFullLicenseByAccountId(int accountId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {l.*} " +
                " FROM license {l} " +
                " WHERE account_id=? AND max_node_count > (SELECT count(*) FROM imei WHERE license_id=l.id)")
                .addEntity("l", License.class);

        return query.setInteger(0, accountId).list();
    }

    public List<License> findByUserId(int userId, Boolean active){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {l.*} " +
                " FROM license {l} " +
                        " JOIN map_user_account mua ON mua.account_id = l.account_id AND mua.user_id = ?" +
                (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1"))).addEntity("l", License.class);

        return query.setInteger(0, userId).list();
    }
    
}
