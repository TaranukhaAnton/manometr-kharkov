package com.sp.dao;

import com.sp.model.BaseSecurityUser;
import com.sp.model.MapUserAccount;
import com.sp.model.SecurityUser;
import com.sp.model.UserPrefs;
import com.sp.util.Constants;
import com.sp.util.Util;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class UserDao extends BaseDao {
    public SecurityUser findByUserName(String userName) {
        Session session = getSession();
        return (SecurityUser) session.createQuery("from com.sp.model.SecurityUser as obj where obj.userName = ?").setString(0, userName).uniqueResult();
    }

    public SecurityUser findByEmail(String email) {
        Session session = getSession();
        return (SecurityUser) session.createQuery("from com.sp.model.SecurityUser as obj where obj.email = ?").setString(0, email).uniqueResult();
    }

    public List<MapUserAccount> findMapUserAccountByAccountId(int accountId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.MapUserAccount as obj where obj.accountId = ?")
                .setInteger(0, accountId).list();
    }

    public List<SecurityUser> findUsersByResellerId(int resellerId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.SecurityUser as obj where obj.reseller.id = ?")
                .setInteger(0, resellerId).list();
    }

    public List<SecurityUser> findMappedByPowerUserId(int userId, Boolean active, boolean driver) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT distinct {u.*} " +
                " FROM map_user_account pu_mua " +
                        " JOIN map_user_account mua ON mua.account_id = pu_mua.account_id" +
                        " JOIN security_user {u} ON u.id = mua.user_id" +
                " WHERE " + (driver ? " is_driver=1 " : " u.access_level != 'ROLE_ADMIN' ")
                        + " AND (u.is_employee = 0 OR (u.is_employee = 1 AND u.id = ?))"
                        + " AND pu_mua.user_id = ? AND is_deleted=" + (active ? "0" : "1"))
                .addEntity("u", SecurityUser.class)
                .setInteger(0, userId)
                .setInteger(1, userId);

        return query.list();
    }

    public MapUserAccount findByAccountIdAndUserId(int accountId, int userId) {
        Session session = getSession();
        return (MapUserAccount) session.createQuery("from com.sp.model.MapUserAccount as obj where obj.accountId = ? and obj.securityUser.id = ?")
                .setInteger(0, accountId).setInteger(1, userId).uniqueResult();
    }

    public List<SecurityUser> findUserListWithAssignedDallasKeyByAccountId(int accountId){
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT distinct {u.*} " +
                        " FROM map_user_account u_mua " +
                        " JOIN security_user {u} ON u.id = u_mua.user_id" +
                        " WHERE u.is_deleted=0 AND u_mua.account_id = ? AND dallas_key_id is not null AND u.is_employee = 0").addEntity("u", SecurityUser.class)
                .setInteger(0, accountId);

        return query.list();
    }

    public List<SecurityUser> findUserListWithAssignedDallasKey(){
        Criteria criteria = getSession().createCriteria(SecurityUser.class);
        criteria.add(Restrictions.eq("deleted", false));
        criteria.add(Restrictions.isNotNull("dallasKey"));
        return criteria.list();
    }

    public BaseSecurityUser findByDallasKeyCode(String dallasKeyCode){
        String sql = " SELECT {su.*} " +
                "FROM security_user {su} " +
                "JOIN dallas_key dk ON dk.id = su.dallas_key_id " +
                "WHERE dk.dallas_key_code = ?";
        Query query = getSession().createSQLQuery(sql).addEntity("su",BaseSecurityUser.class).setString(0,dallasKeyCode);
        return (BaseSecurityUser)query.uniqueResult();
    }

    public List<SecurityUser> findDrivers(int curUserId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT distinct {u.*} " +
                " FROM map_user_account u_mua " +
                        " JOIN map_user_account mua ON mua.account_id = u_mua.account_id" +
                        " JOIN account a ON a.id = mua.account_id" +
                        " JOIN security_user {u} ON u.id = mua.user_id" +
                " WHERE u.is_driver=1 AND u.is_deleted=0 AND u_mua.user_id = ?").addEntity("u", SecurityUser.class)
                .setInteger(0, curUserId);

        return query.list();
    }

    public List<SecurityUser> findAllDrivers() {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {u.*} " +
                "FROM security_user u " +
                " WHERE u.is_driver=1 AND u.is_deleted=0").addEntity("u", SecurityUser.class);
        return query.list();
    }

    public List<SecurityUser> findPhoneUsers(int curUserId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT distinct {u.*} " +
                " FROM map_user_account u_mua " +
                        " JOIN map_user_account mua ON mua.account_id = u_mua.account_id" +
                        " JOIN account a ON a.id = mua.account_id" +
                        " JOIN security_user {u} ON u.id = mua.user_id" +
                " WHERE  u.is_phone_user=1 AND u.is_deleted=0 AND u_mua.user_id = ?").addEntity("u", SecurityUser.class)
                .setInteger(0, curUserId);

        return query.list();
    }

    public Map<Constants.NODES_TYPE_NAME,String> findAllUserDisablings(int curUserId, boolean withAllChildren){
        Session session = getSession();
        String sql;
        if(withAllChildren){
            sql = "select 'vehicle' type, uud.unit_id item_id from user_unit_disabling uud where uud.user_id = :userId " +
                "UNION ALL " +
                "select distinct 'vehicle' type, vu.id item_id from unit vu " +
                "join map_unit_view_unit vmuv on vmuv.vehicle_id = vu.id " +
                "where vmuv.group_id in (select vuuvd.unit_view_id from user_unit_view_disabling vuuvd where vuuvd.user_id = :userId " +
                "      union " +
                "      select vuv.id from unit_view vuv where vuv.account_id in (select vuad.account_id from user_account_disabling vuad where vuad.user_id = :userId) " +
                ")" +
                "UNION ALL " +
                "select 'group' type ,uuvd.unit_view_id item_id from user_unit_view_disabling uuvd " +
                "where uuvd.user_id = :userId " +
                "UNION ALL " +
                "select DISTINCT 'group' type ,guv.id item_id from unit_view guv " +
                "where guv.account_id in (select uad.account_id from user_account_disabling uad where uad.user_id = :userId) " +
                "UNION ALL " +
                "select 'account' type,uad.account_id item_id from user_account_disabling uad where uad.user_id = :userId";
        }else{
            sql = "select 'vehicle' type, uud.unit_id item_id from user_unit_disabling uud where uud.user_id = :userId " +
                "UNION ALL " +
                "select 'group' type ,uuvd.unit_view_id item_id from user_unit_view_disabling uuvd where uuvd.user_id = :userId " +
                "UNION ALL " +
                "select 'account' type,uad.account_id item_id from user_account_disabling uad where uad.user_id = :userId";
        }
        List<Object[]> objList = session.createSQLQuery(sql).setInteger("userId",curUserId).list();
        Set<String> vehicleIdList = new HashSet<String>();
        Set<String> groupIdList = new HashSet<String>();
        Set<String> accountIdList = new HashSet<String>();
        for(Object[] o : objList){
            if(String.valueOf(o[0]).equals("vehicle")){
                vehicleIdList.add(String.valueOf(o[1]));
            }else if(String.valueOf(o[0]).equals("group")){
                groupIdList.add(String.valueOf(o[1]));
            }else if(String.valueOf(o[0]).equals("account")){
                accountIdList.add(String.valueOf(o[1]));
            }
        }
        Map<Constants.NODES_TYPE_NAME,String> res = new HashMap<Constants.NODES_TYPE_NAME, String>();
        res.put(Constants.NODES_TYPE_NAME.Vehicle, Util.joinListByComma(new ArrayList<String>(vehicleIdList)));
        res.put(Constants.NODES_TYPE_NAME.UnitView, Util.joinListByComma(new ArrayList<String>(groupIdList)));
        res.put(Constants.NODES_TYPE_NAME.Account, Util.joinListByComma(new ArrayList<String>(accountIdList)));
        return res;
    }

    public void deleteRestrictedVehiclesMapByUserId(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery("delete from user_unit_disabling where user_id = ?");
        query.setInteger(0, userId).executeUpdate();
    }

    public void deleteRestrictedUnitGroupsMapByUserId(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery("delete from user_unit_view_disabling where user_id = ?");
        query.setInteger(0, userId).executeUpdate();
    }

    public void deleteRestrictedAccountsMapByUserId(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery("delete from user_account_disabling where user_id = ?");
        query.setInteger(0, userId).executeUpdate();
    }

    public List<SecurityUser> findMovableItemsUsers(String fildName, int curUserId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT distinct {u.*} " +
                " FROM map_user_account u_mua " +
                        " JOIN map_user_account mua ON mua.account_id = u_mua.account_id" +
                        " JOIN account a ON a.id = mua.account_id" +
                        " JOIN security_user {u} ON u.id = mua.user_id" +
                " WHERE u."+fildName+"=1 AND u.is_deleted=0 AND u_mua.user_id = ?").addEntity("u", SecurityUser.class)
                .setInteger(0, curUserId);

        return query.list();
    }

    public List<SecurityUser> findEngineers() {
        Session session = getSession();
        return session.createQuery("from com.sp.model.SecurityUser as obj where obj.engineer = true").list();
    }

    public UserPrefs findPrefsByUserId(int userId) {
        Session session = getSession();
        return (UserPrefs) session.createQuery("from com.sp.model.UserPrefs as obj where obj.userId = ?").setInteger(0, userId).uniqueResult();
    }

    public List<MapUserAccount> findMapUserAccountByUserId(int userId) {
        return findMapUserAccountByUserId(userId, null);
    }

    public List<MapUserAccount> findMapUserAccountByUserId(int userId, Integer resellerId) {
        Session session = getSession();
        Query query = session.createSQLQuery("SELECT {mua.*} " +
                " FROM map_user_account mua " +
                " JOIN account a ON a.id = mua.account_id" +
                " where mua.user_id = ? "
                + (resellerId == null ? "" : "and a.reseller_id = ?"))
                .addEntity("mua", MapUserAccount.class)
                .setInteger(0, userId);
        if (resellerId != null) {
            query = query.setInteger(1, resellerId);
        }
        return query.list();
    }

    public Set<String> getAllowedIPs(int userId) {
        Session session = getSession();
        Query query = session.createSQLQuery("select  a.allowed_ip_list as ip_list from  map_user_account m, account a, reseller r where " +
                "a.id = m.account_id and r.id = a.reseller_id and  m.user_id = ?  and r.is_use_ip_filter = 1 and " +
                "((select count(*) from  map_user_account m where m.user_id = ?)=1)").addScalar("ip_list", Hibernate.STRING)
                .setInteger(0, userId).setInteger(1, userId);
        String ipListStr = (String) query.uniqueResult();
        Set<String> result = new HashSet<String>();
        if (StringUtils.isNotEmpty(ipListStr)) {
            String[] split = ipListStr.split(";");
            for (String s : split) {
                result.add(s.replace(" ", ""));
            }
        }
        return result;
    }

    public SecurityUser findUserByUsernameAndPass(String username,String password){
        Criteria criteria = getSession().createCriteria(SecurityUser.class);
        criteria.add(Restrictions.eq("userName", username));
        criteria.add(Restrictions.eq("password", password));
        return (SecurityUser)criteria.uniqueResult();
    }

    public List<SecurityUser> findUsualAdminList(){
        Criteria criteria = getSession().createCriteria(SecurityUser.class);
        criteria.add(Restrictions.eq("usualAdministrator", true));
        return criteria.list();
    }
}
