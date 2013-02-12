package com.sp.dao;

import com.sp.dto.TreeItem;
import com.sp.dto.mobile.VehicleMobile;
import com.sp.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao extends BaseDao {

    public static final int LEFT_TREE_ITEM_TYPE_POSITION = 0;
    public static final int LEFT_TREE_ACCOUNT_ID_POSITION = 1;
    public static final int LEFT_TREE_ACCOUNT_DESCR_POSITION = 2;
    public static final int LEFT_TREE_RESELLER_ID_POSITION = 3;
    public static final int LEFT_TREE_RESELLER_DESCR_POSITION = 4;
    public static final int LEFT_TREE_GROUP_ID_POSITION = 5;
    public static final int LEFT_TREE_GROUP_DESCR_POSITION = 6;
    public static final int LEFT_TREE_GROUP_PARENT_ID_POSITION = 7;
    public static final int LEFT_TREE_LOW_LEVEL_NODE_TYPE = 8;

    public List<Account> findByResellerId(int resellerId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.Account as obj where obj.reseller.id = ?")
                .setInteger(0, resellerId).list();
    }

    public List<Account> findActiveByResellerId(int resellerId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.Account as obj where obj.reseller.id = ? and obj.deleted = false")
                .setInteger(0, resellerId).list();
    }

    public void setCountdownForAllAccountsByParnter(int partnerId, boolean countdounAllowed){
        Session session = getSession();
        session.createSQLQuery("update account set is_countdown_refresh_sm_allowed = ?, refresh_sm_seconds = 60 where reseller_id = ?")
                .setBoolean(0,countdounAllowed).setInteger(1,partnerId).executeUpdate();
    }

    public void resetAccountsBooleanValForReseller(int resellerId, String field){
        Session session = getSession();
        session.createSQLQuery("update account set "+field+" = 0 where reseller_id = ?")
                .setInteger(0,resellerId).executeUpdate();
    }

    public List<Account> findByUserId(int userId) {
        return findByUserId(userId, null);
    }

    public List<Account> findActiveByUserIdResellserId(int userId, int resellerId){
        Session session = getSession();
        Query query = session.createSQLQuery(
              "SELECT {a.*}  " +
                        " FROM account {a}" +
                        " JOIN map_user_account mua ON mua.account_id = a.id AND mua.user_id = ?" +
                        " WHERE is_deleted=0 and a.reseller_id = ?").addEntity("a", Account.class);
        return query.setInteger(0,userId).setInteger(1,resellerId).list();
    }

    public List<Account> findByUserId(int userId, Boolean active) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {a.*}  " +
                        " FROM account {a}" +
                        " JOIN map_user_account mua ON mua.account_id = a.id AND mua.user_id = ?" +
                        (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1"))).addEntity("a", Account.class);

        return query.setInteger(0, userId).list();
    }

    public List<Account> findByUnitIds(List<Integer> unitIdList) {
         Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT DISTINCT {a.*}  " +
                        " FROM account {a}" +
                        " JOIN map_unit_account mua ON mua.account_id = a.id AND mua.unit_id in (:unitIdList)" +
                        " WHERE is_deleted=0").addEntity("a", Account.class);

        return query.setParameterList("unitIdList", unitIdList).list();
    }

    private StringBuilder getSqlForVehicleTree(Integer userId){
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

    public List<TreeItem> findAllNotExpiredVehiclesTree(Integer userId){
        Session session = getSession();
        StringBuilder sql = getSqlForVehicleTree(userId);
        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger(0, userId).setInteger(1, userId).setInteger(2, userId).setInteger(3, userId);
        }
        return parseSqlResult(query.list());
    }

    public List<TreeItem> findAllItemsForTreeNotExpired(Integer userId) {
        Session session = getSession();
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
        if(userId !=null){
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
        if(userId !=null){
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ah.id) ");
        }
        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger("userId",userId);
        }
        return parseSqlResult(query.list());
    }

    public List<TreeItem> findAllItemsForTree(Integer userId) {
        Session session = getSession();
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
        if(userId !=null){
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
        if(userId !=null){
            sql.append(" AND NOT exists (select null from user_account_disabling uad where uad.user_id = :userId AND uad.account_id = ah.id) ");
        }

        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger("userId",userId);
        }
        return parseSqlResult(query.list());
    }

    public List<VehicleMobile> findAllItemsForPermissionTreeMobile(Integer userId) {
        Session session = getSession();
        StringBuilder sql = new StringBuilder();

        sql.append("select distinct u.descr ud from unit u ");
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


        Query query = session.createSQLQuery(sql.toString());
        if (userId != null) {
            query.setInteger("userId",userId);
        }
        List<Object[]> objects = query.list();
        return parseVehicleDtoResult(objects);
    }

    private List<VehicleMobile> parseVehicleDtoResult(List<Object[]> objects) {
        List<VehicleMobile> vehicles = new ArrayList<VehicleMobile>();
        for (Object[] o : objects) {
            VehicleMobile dto = new VehicleMobile();
            if (o[LEFT_TREE_ITEM_TYPE_POSITION] != null) {
                dto.setVehReg(o[LEFT_TREE_ITEM_TYPE_POSITION].toString());
            }

            vehicles.add(dto);
        }

        return vehicles;
    }

    /**
     * Find all items for permission tree and store them in TreeItem objects.
     * @param userId - User which permission should be loaded.
     * @param curUserId - User which is looking for other user's permissions.
     * @return - List of items for tree.
     */
    public List<TreeItem> findAllItemsForPermissionTree(Integer userId, Integer curUserId) {
        Session session = getSession();
        StringBuilder sql = new StringBuilder();
       
        sql.append("select distinct 'vehicle' iu ,uv.account_id uvaid, au.descr aud, ru.id rui, ru.descr rud, uv.id uvi, uv.descr uvd, uv.parent_id uvp, 'UnitView' lle from unit u ");
        sql.append("JOIN imei i ON i.unit_id = u.id ");
        sql.append("JOIN license lu ON lu.id = i.license_id ");
        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
        sql.append("join unit_view uv on uv.id = muvu.group_id ");
        sql.append("join account au on au.id = uv.account_id ");
        if(userId!=null){
            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
        }
        sql.append("join reseller ru on ru.id = au.reseller_id ");
        sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) ");
        if (curUserId != null) {
            sql.append("and au.id in (select account_id from map_user_account where user_id = :curUserId) ");
        }
        sql.append("and au.is_deleted = 0 and u.is_deleted=0 AND CURDATE() BETWEEN lu.activation_date AND lu.end_date ");

        sql.append("union all ");
        sql.append("select distinct 'vehicle' iu ,uv.account_id uvaid, au.descr aud, ru.id rui, ru.descr rud, u.id ui, u.registration_number uvd, uv.id uvi, 'Vehicle' lle from unit u ");
        sql.append("JOIN imei i ON i.unit_id = u.id ");
        sql.append("JOIN license lu ON lu.id = i.license_id ");
        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
        sql.append("join unit_view uv on uv.id = muvu.group_id ");
        sql.append("join account au on au.id = uv.account_id ");
        if(userId!=null){
            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
        }
        sql.append("join reseller ru on ru.id = au.reseller_id ");
        sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) ");
        if (curUserId != null) {
            sql.append("and au.id in (select account_id from map_user_account where user_id = :curUserId) ");
        }
        sql.append("and au.is_deleted = 0 and u.is_deleted=0 AND CURDATE() BETWEEN lu.activation_date AND lu.end_date ");

        Query query = session.createSQLQuery(sql.toString());
        if(curUserId!=null){
            query.setInteger("curUserId",curUserId);
        }
        if(userId!=null){
            query.setInteger("userId",userId);
        }
        return parseSqlResult(query.list());
    }

//    public List<TreeItem> findAllItemsForPermissionTreeForPerm(Integer userId, boolean admin) {
//        Session session = getSession();
//        StringBuilder sql = new StringBuilder();
//
//        sql.append("select distinct 'vehicle' iu ,uv.account_id uvaid, au.descr aud, ru.id rui, ru.descr rud, uv.id uvi, uv.descr uvd, uv.parent_id uvp, 0 as selected, 'UnitView' lle from unit u ");
//        sql.append("JOIN imei i ON i.unit_id = u.id ");
//        sql.append("JOIN license lu ON lu.id = i.license_id ");
//        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
//        sql.append("join unit_view uv on uv.id = muvu.group_id ");
//        sql.append("join account au on au.id = uv.account_id ");
//        if(!admin){
//            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
//        }
//        sql.append("join reseller ru on ru.id = au.reseller_id ");
//        //sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) ");
//        sql.append("WHERE au.is_deleted = 0 and u.is_deleted=0 AND CURDATE() BETWEEN lu.activation_date AND lu.end_date ");
//
//        sql.append("union all ");
//        sql.append("select distinct 'vehicle' iu ,uv.account_id uvaid, au.descr aud, ru.id rui, ru.descr rud, u.id ui, u.registration_number uvd, uv.id uvi, 0 as selected, 'Vehicle' lle from unit u ");
//        sql.append("JOIN imei i ON i.unit_id = u.id ");
//        sql.append("JOIN license lu ON lu.id = i.license_id ");
//        sql.append("join map_unit_view_unit muvu on muvu.vehicle_id = u.id ");
//        sql.append("join unit_view uv on uv.id = muvu.group_id ");
//        sql.append("join account au on au.id = uv.account_id ");
//        if(!admin){
//            sql.append("JOIN map_user_account muau ON muau.account_id = au.id AND muau.user_id = :userId ");
//        }
//        sql.append("join reseller ru on ru.id = au.reseller_id ");
//        //sql.append("WHERE muvu.group_id in (select id from unit_view where account_id = uv.account_id) ");
//        sql.append("WHERE au.is_deleted = 0 and u.is_deleted=0 AND CURDATE() BETWEEN lu.activation_date AND lu.end_date ");
//
//        Query query = session.createSQLQuery(sql.toString());
//
//        if(!admin){
//            query.setInteger("userId",userId);
//        }
//        return parseSqlResult(query.list());
//    }


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
           if(o[LEFT_TREE_LOW_LEVEL_NODE_TYPE]!=null){
                ti.setLowLevelNodeType(o[LEFT_TREE_LOW_LEVEL_NODE_TYPE].toString());
            }
            treeItems.add(ti);
        }
        return treeItems;
    }

    public Account findByUnitViewId(int unitViewId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
        "SELECT {a.*}  " +
                " FROM account {a}" +
                " WHERE is_deleted=0 and a.id in (select account_id from unit_view where id = ?)").addEntity("a", Account.class);
        return (Account) query.setInteger(0,unitViewId).uniqueResult();

    }
}
