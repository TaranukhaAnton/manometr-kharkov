package com.sp.dao;

import com.sp.model.Asset;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class AssetDao extends BaseDao {
    public List<Asset> findAllUnassigned(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {a.*} " +
                " FROM tag {a} " +
                    " JOIN map_user_account mua ON mua.account_id = a.id AND mua.user_id = ?" +
                " WHERE NOT EXISTS (SELECT NULL FROM security_user WHERE tag_id = a.id)").addEntity("a", Asset.class).setInteger(0, userId);

        return query.list();
    }

    public List<Asset> findByAccountId(int accountId) {
        Session session = getSession();
        return (List<Asset>) session.createQuery("from com.sp.model.Asset  as obj where obj.account.id = ?")
                .setInteger(0, accountId).list();
    }

    public List<Asset> findByUserId(int userId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {t.*} " +
                " FROM tag {t} " +
                        " JOIN map_user_account mua ON mua.account_id = t.account_id AND mua.user_id = ?")
                .addEntity("t", Asset.class);

        return query.setInteger(0, userId).list();
    }
}
