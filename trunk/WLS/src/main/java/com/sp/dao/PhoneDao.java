package com.sp.dao;


import com.sp.model.MapPhoneAccount;
import com.sp.model.Phone;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ananda
 * Date: Mar 21, 2009
 * Time: 11:28:32 AM
 */
public class PhoneDao extends BaseDao {
    public List<Phone> findBySearchStr(String searchStr, boolean includeDeleted) {
        Session session = getSession();
        Criteria c = session.createCriteria(Phone.class);

        if (searchStr != null && searchStr.length() > 0) {
            c.add(Restrictions.ilike("phoneNumber", "%" + searchStr + "%"));
        }

        if (!includeDeleted) {
            c.add(Restrictions.eq("deleted", false));
        }

        c.addOrder(Order.asc("id"));
        return (List<Phone>) c.list();
    }

    public Phone findByImei(String imei) {
        Session session = getSession();
        return (Phone) session.createQuery("from com.sp.model.Phone as obj where obj.imei = ?").setString(0, imei).uniqueResult();
    }

    public List<MapPhoneAccount> findMapPhoneAccountsByPhoneId(int phoneId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.MapPhoneAccount as obj where obj.phoneId = ?")
                .setInteger(0, phoneId).list();
    }

    public List<Phone> findNonExpiredByPhoneViewId(int phoneViewId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {p.*} FROM phone_unit {p} "
                        + " JOIN map_phone_unit mpu ON mpu.phone_id = p.id"
                        + " JOIN license l ON l.id = p.license_id"
                        + " WHERE  mpu.group_id=? AND p.is_deleted=0 AND CURDATE() BETWEEN l.activation_date AND l.end_date")
                .addEntity("p", Phone.class);

        return query.setInteger(0, phoneViewId).list();
    }

    public List<Phone> findForUserId(int userId, Boolean active) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {u.*} " +
                        " FROM phone_unit {u} " +
                        " JOIN map_phone_account muacc ON muacc.unit_id = u.id " +
                        " JOIN map_user_account mua ON mua.account_id = muacc.account_id AND mua.user_id = ?" +
                        (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1")))
                .addEntity("u", Phone.class);

        return query.setInteger(0, userId).list();
    }

    public List<Phone> findByAccountId(int accountId) {
        Session session = getSession();
        Query query = session.createSQLQuery("select {p.*} from phone_unit {p}" +
                " JOIN map_phone_account mpa ON mpa.unit_id = p.id " +
                " where mpa.account_id = ?").addEntity("p", Phone.class);
        return (List<Phone>) query.setInteger(0, accountId).list();
    }

    public List<Phone> findByLicenseId(int licenseId) {
        Session session = getSession();
        return (List<Phone>) session.createQuery("from com.sp.model.Phone  as obj where obj.licenseId = ?")
                .setInteger(0, licenseId).list();
    }

    public List<Phone> findPhonesExceptSelectedForAdmin(int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select {p.*}" +
                        " from phone_unit {p}" +
                        " where p.id not in (select phone_id from map_alert_phone where alert_id = ?)"
        ).addEntity("p", Phone.class);
        return (List<Phone>) query.setInteger(0, alertId).list();
    }

    public List<Phone> findPhonesExceptSelectedForUser(int userId, int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select distinct {p.*}" +
                        " from phone_unit {p}" +
                        " join map_phone_account mpacc on mpacc.unit_id = p.id " +
                        " join map_user_account mua on mua.account_id = mpacc.account_id AND mua.user_id = ?" +
                        " where p.id not in (select phone_id from map_alert_phone where alert_id = ?)"
        ).addEntity("p", Phone.class);
        return (List<Phone>) query.setInteger(0, userId).setInteger(1, alertId).list();
    }

}

