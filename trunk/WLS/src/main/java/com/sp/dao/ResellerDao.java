package com.sp.dao;

import com.sp.model.Reseller;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.DistinctRootEntityResultTransformer;
import org.hibernate.transform.ResultTransformer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


/**
 * User: andrey
 */
public class ResellerDao extends BaseDao {
    private static final ResultTransformer trans = new DistinctRootEntityResultTransformer();

    public List<Reseller> findAllowedResellers(int userId){
        Query query = getSession().createSQLQuery(
                "SELECT DISTINCT {r.*} " +
                " FROM map_user_account mua " +
                        " JOIN account a ON a.id = mua.account_id" +
                        " JOIN reseller r ON r.id = a.reseller_id" +
                " WHERE mua.user_id = ? and r.is_deleted = 0").addEntity("r", Reseller.class)
                .setInteger(0, userId);

        return query.list();
    }

    public Reseller findResellerByUrlCodePrefix(String urlPrefix) {
        Query query = getSession().createQuery(
            "from com.sp.model.Reseller as r where r.urlCodePrefix = :urlPrefix");
        query.setParameter("urlPrefix", urlPrefix);

        return (Reseller) query.uniqueResult();
    }

    /**
     * SELECT DISTINCT
  (reseller.id) AS FIELD_1,
  reseller.descr,
  security_user.user_name
FROM
  reseller,
  security_user,
  account,
  map_user_account
WHERE
  reseller.id = account.reseller_id AND
  account.id = map_user_account.account_id AND
  map_user_account.user_id = security_user.id AND
  security_user.id = 1
     * @param userId
     * @param host
     * @return
     */
    public List<Reseller> findResellersOfUserByUserId(int userId, String host) {
        Session session = getSession();
        /*Query query = session.createSQLQuery("SELECT DISTINCT (reseller.id) AS FIELD_1," +
                "reseller.descr, security_user.user_name FROM reseller, security_user,  account,  map_user_account" +
                " WHERE  reseller.id = account.reseller_id " +
                "AND  account.id = map_user_account.account_id " +
                "AND  map_user_account.user_id = security_user.id " +
                "AND security_user.id = :userId");

        query.setParameter("userId",userId);
        */
        Query query = session.createSQLQuery(
                "SELECT {rs.*} " +
                " FROM reseller {rs} " +
                    " JOIN account a ON a.reseller_id = rs.id " +
                    " JOIN map_user_account mua ON a.id = mua.account_id " +
                    " WHERE mua.user_id = ? AND rs.url_code_prefix = ?")
                .addEntity("rs", Reseller.class);

        query.setInteger(0, userId);
        query.setString(1, host) ;
        return trans.transformList(query.list());
    }

    public List<Reseller> findByIncomingLogProcessorServiceId(String serviceId) {
        Criteria criteria = getSession().createCriteria(Reseller.class);
        criteria.add(Restrictions.eq("incomingLogProcessorServiceId", serviceId));
        criteria.add(Restrictions.eq("deleted", false));
        return criteria.list();
    }

    public static String findRebrandingFolderByUrlCodePrefix(Connection connection, String urlPrefix) throws SQLException {
        Statement statement = null;
        ResultSet rs;
        String result = "";
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select * from reseller where url_code_prefix='" + urlPrefix + "'");
            if (rs.next()) {
                result = rs.getString("rebranding_folder");
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
        return result;
    }
}
