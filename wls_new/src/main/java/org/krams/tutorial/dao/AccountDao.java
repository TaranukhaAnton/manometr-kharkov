package org.krams.tutorial.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.krams.tutorial.dto.TreeItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao {
    public static final int LEFT_TREE_ITEM_TYPE_POSITION = 0;
    public static final int LEFT_TREE_ACCOUNT_ID_POSITION = 1;
    public static final int LEFT_TREE_ACCOUNT_DESCR_POSITION = 2;
    public static final int LEFT_TREE_RESELLER_ID_POSITION = 3;
    public static final int LEFT_TREE_RESELLER_DESCR_POSITION = 4;
    public static final int LEFT_TREE_GROUP_ID_POSITION = 5;
    public static final int LEFT_TREE_GROUP_DESCR_POSITION = 6;
    public static final int LEFT_TREE_GROUP_PARENT_ID_POSITION = 7;
    public static final int LEFT_TREE_LOW_LEVEL_NODE_TYPE = 8;


    @Autowired
    SessionFactory sessionFactory;


    public List<TreeItem> findAllItemsForTreeNotExpired(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct 'phone' pi,pv.account_id pvaid, ap.descr apd, rp.id rpi, rp.descr rpd, pv.id pvi, pv.descr pvd, pv.parent_id pvp, 0, 'UnitView' lle ");
        sql.append("from phone_unit p ");
        sql.append("JOIN license lp ON lp.id = p.license_id ");
        sql.append("join map_phone_unit mpu on mpu.phone_id = p.id ");
        sql.append("join phone_view pv on pv.id = mpu.group_id ");
        sql.append("join account ap on ap.id = pv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muap ON muap.account_id = ap.id AND muap.user_id = :userId ");
        }
        sql.append("join reseller rp on rp.id = ap.reseller_id ");
        sql.append("WHERE mpu.group_id in (select id from phone_view where account_id = pv.account_id) ");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ap.id) ");
        }
        sql.append("and ap.is_deleted = 0 and p.is_deleted=0 AND CURDATE() BETWEEN lp.activation_date AND lp.end_date ");
        sql.append("union all ");
        sql.append(getSqlForVehicleTree(userId));
        sql.append("union all ");
        sql.append("select distinct 'handheld' ih,hv.account_id hva, ah.descr ahd, ah.reseller_id ahi, rh.descr rhd, hv.id hvi, hv.descr hvd, hv.parent_id hvp, 0, 'UnitView' lle from handheld h ");
        sql.append("JOIN license lh ON lh.id = h.license_id ");
        sql.append("join map_handheld_view mhv on mhv.unit_id = h.id ");
        sql.append("join handheld_view hv on hv.id = mhv.group_id ");
        sql.append("join account ah on ah.id = hv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muah ON muah.account_id = ah.id AND muah.user_id = :userId ");
        }
        sql.append("join reseller rh on rh.id = ah.reseller_id ");
        sql.append("WHERE mhv.group_id in (select id from handheld_view where account_id = hv.account_id) ");
        sql.append("and ah.is_deleted = 0 and h.is_deleted=0 AND CURDATE() BETWEEN lh.activation_date AND lh.end_date ");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ah.id) ");
        }
        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger("userId", userId);
        }
        return parseSqlResult(query.list());
    }

    public List<TreeItem> findAllItemsForTree(Integer userId) {
        Session session = sessionFactory.getCurrentSession();
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct 'phone' pi,pv.account_id pvaid, ap.descr apd, rp.id rpi, rp.descr rpd, pv.id pvi, pv.descr pvd, pv.parent_id pvp, 0, 'UnitView' lle");
        sql.append("from phone_unit p ");
        sql.append("join map_phone_unit mpu on mpu.phone_id = p.id ");
        sql.append("join phone_view pv on pv.id = mpu.group_id ");
        sql.append("join account ap on ap.id = pv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muap ON muap.account_id = ap.id AND muap.user_id = :userId ");
        }
        sql.append("join reseller rp on rp.id = ap.reseller_id ");
        sql.append("WHERE mpu.group_id in (select id from phone_view where account_id = pv.account_id) and ap.is_deleted = 0");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ap.id) ");
        }
        sql.append("union all ");
        sql.append("select distinct 'vehicle' iv ,uv.account_id uvaid, au.descr aud, ru.id rui, ru.descr rud, uv.id uvi, uv.descr uvd, uv.parent_id uvp, 0, 'UnitView' lle from unit u ");
        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
        sql.append("join unit_view uv on uv.id = muvu.group_id ");
        sql.append("join account au on au.id = uv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
        }
        sql.append("join reseller ru on ru.id = au.reseller_id ");
        sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) and au.is_deleted = 0 and ru.is_deleted = 0");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_unit_disabling uud where uud.user_id = :userId AND uud.unit_id = u.id) ");
            sql.append(" AND NOT exists (select null from user_unit_view_disabling uuvd where uuvd.user_id = :userId AND uuvd.unit_view_id = uv.id) ");
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = au.id) ");
        }
        sql.append("union all ");
        sql.append("select distinct 'handheld' ih,hv.account_id hva, ah.descr ahd, ah.reseller_id ahi, rh.descr rhd, hv.id hvi, hv.descr hvd, hv.parent_id hvp, 0, 'UnitView' lle from handheld h ");
        sql.append("join map_handheld_view mhv on mhv.unit_id = h.id ");
        sql.append("join handheld_view hv on hv.id = mhv.group_id ");
        sql.append("join account ah on ah.id = hv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muah ON muah.account_id = ah.id AND muah.user_id = :userId ");
        }
        sql.append("join reseller rh on rh.id = ah.reseller_id ");
        sql.append("WHERE mhv.group_id in (select id from handheld_view where account_id = hv.account_id) and ah.is_deleted = 0");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ah.id) ");
        }

        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger("userId", userId);
        }
        return parseSqlResult(query.list());
    }


    private StringBuilder getSqlForVehicleTree(Integer userId) {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct 'vehicle' iv, uv.account_id, au.descr, ru.id, ru.descr, uv.id, uv.descr, uv.parent_id, 0, 'UnitView' lle from unit u ");
        sql.append("JOIN imei i ON i.unit_id = u.id ");
        sql.append("JOIN license lu ON lu.id = i.license_id ");
        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
        sql.append("join unit_view uv on uv.id = muvu.group_id ");
        sql.append("join account au on au.id = uv.account_id ");
        if (userId != null) {
            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
        }
        sql.append("join reseller ru on ru.id = au.reseller_id ");
        sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) ");
        sql.append("and au.is_deleted = 0 and u.is_deleted=0 AND ru.is_deleted = 0 AND CURDATE() BETWEEN lu.activation_date AND lu.end_date ");
        if (userId != null) {
            sql.append(" AND NOT exists (select null from user_unit_disabling uud where uud.user_id = :userId AND uud.unit_id = u.id) ");
            sql.append(" AND NOT exists (select null from user_unit_view_disabling uuvd where uuvd.user_id = :userId AND uuvd.unit_view_id = uv.id) ");
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = au.id) ");
        }
        return sql;
    }


    private List<TreeItem> parseSqlResult(List<Object[]> objects) {
        List<TreeItem> treeItems = new ArrayList<TreeItem>();
        for (Object[] o : objects) {
            TreeItem ti = new TreeItem();
            if (o[LEFT_TREE_ITEM_TYPE_POSITION] != null) {
                ti.setItemType(o[LEFT_TREE_ITEM_TYPE_POSITION].toString());
            }
            if (o[LEFT_TREE_ACCOUNT_ID_POSITION] != null) {
                ti.setAccountId(Integer.parseInt(o[LEFT_TREE_ACCOUNT_ID_POSITION].toString()));
            }
            if (o[LEFT_TREE_ACCOUNT_DESCR_POSITION] != null) {
                ti.setAccountDescription(o[LEFT_TREE_ACCOUNT_DESCR_POSITION].toString());
            }
            if (o[LEFT_TREE_RESELLER_ID_POSITION] != null) {
                ti.setResellerId(Integer.parseInt(o[LEFT_TREE_RESELLER_ID_POSITION].toString()));
            }
            if (o[LEFT_TREE_RESELLER_DESCR_POSITION] != null) {
                ti.setResellerDescription(o[LEFT_TREE_RESELLER_DESCR_POSITION].toString());
            }
            if (o[LEFT_TREE_GROUP_ID_POSITION] != null) {
                ti.setGroupId(Integer.parseInt(o[LEFT_TREE_GROUP_ID_POSITION].toString()));
            }
            if (o[LEFT_TREE_GROUP_DESCR_POSITION] != null) {
                ti.setGroupDescription(o[LEFT_TREE_GROUP_DESCR_POSITION].toString());
            }
            if (o[LEFT_TREE_GROUP_PARENT_ID_POSITION] != null) {
                ti.setGroupParentId(Integer.parseInt(o[LEFT_TREE_GROUP_PARENT_ID_POSITION].toString()));
            }
            if (o[LEFT_TREE_LOW_LEVEL_NODE_TYPE] != null) {
                ti.setLowLevelNodeType(o[LEFT_TREE_LOW_LEVEL_NODE_TYPE].toString());
            }
            treeItems.add(ti);
        }
        return treeItems;
    }

}
