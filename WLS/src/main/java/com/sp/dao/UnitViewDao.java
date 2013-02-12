package com.sp.dao;

import com.sp.model.UnitView;
import com.sp.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class UnitViewDao extends BaseDao {
    public List<UnitView> findByParentId(int parentId) {
        Session session = getSession();
        return (List<UnitView>) session.createQuery("from com.sp.model.UnitView  as obj where obj.parent.id = ?").setInteger(0, parentId).list();
    }

    public List<UnitView> findRootsByAccountId(int userId, int accountId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT DISTINCT {uv.*} " +
                " FROM unit_view {uv} " +
                        " JOIN map_unit_view_unit muvu ON muvu.group_id = uv.id " +
                        " JOIN unit u ON u.id = muvu.vehicle_id " +
                        " JOIN imei i ON i.unit_id = u.id " +
                        " JOIN license l ON l.id = i.license_id " +
                        " WHERE uv.parent_id IS null AND uv.account_id = ? AND u.is_deleted = 0" +
                        " AND CURDATE() BETWEEN l.activation_date AND l.end_date " +
                        " AND NOT EXISTS (SELECT NULL FROM user_unit_disabling uud " +
                                        " WHERE uud.unit_id = muvu.vehicle_id AND uud.user_id = ?)" +
                        " AND NOT EXISTS (SELECT NULL FROM user_unit_view_disabling uuvd where uuvd.user_id = ?" +
                                        " and uuvd.unit_view_id = uv.id)")
                .addEntity("uv", UnitView.class);
        return  query.setInteger(0, accountId).setInteger(1, userId).setInteger(2, userId).list();
    }

    public UnitView findUnassignedGroupByAccount(int accountId){
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return (UnitView) session.createQuery("from com.sp.model.UnitView as obj where obj.descr like ? and obj.account.id = ?")
                .setString(0, unassignedStr).setInteger(1,accountId).uniqueResult();
    }

    public List<UnitView> findAllUnassignedGroups(){
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return (List<UnitView>) session.createQuery("from com.sp.model.UnitView as obj where obj.descr like ?")
                .setString(0,unassignedStr).list();
    }

    public int findCountMapsForUnassignedGroup(int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery("select count(*) from map_unit_view_unit where group_id = ?");
        return Integer.parseInt(query.setInteger(0,groupId).uniqueResult().toString());
                //delete from map_unit_view_unit where vehicle_id = ? and group_id = ?

    }

    public List<UnitView> findRootsByResellerId(int resellerId) {
        Session session = getSession();
        return (List<UnitView>) session
            .createQuery("from com.sp.model.UnitView  as obj where obj.parent = null and obj.account.reseller.id = ?")
                .setInteger(0, resellerId).list();
    //join com.sp.model.Account acc join com.sp.model.Reseller res
    }

    public List<UnitView> findAllByUserId(int userId) {
        return findAllByUserId(userId, "");
    }

    public List<UnitView> findAllByUserId(int userId, String restrictedGroups) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT DISTINCT {uv.*} " +
                        " FROM unit_view {uv} " +
                        " JOIN map_user_account mua ON mua.account_id = uv.account_id AND mua.user_id = ?" +
                        (restrictedGroups.length() > 0 ? " AND uv.id NOT IN (" + restrictedGroups + ")" : ""))
                .addEntity("uv", UnitView.class);

        return query.setInteger(0, userId).list();
    }

    public List<UnitView> findUnitViewsMobile(int userId, boolean isAdmin) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT distinct {uv.*} " +
                        " FROM unit_view {uv} " +
                        " JOIN map_user_account mua ON mua.account_id = uv.account_id" +
                        (isAdmin ? " " : " AND mua.user_id = ?") +
                        " JOIN account a on a.id = mua.account_id " +
                        " WHERE a.is_deleted = 0" +
                        (isAdmin ? " " : " AND a.is_ios_application_allowed = 1"))
                .addEntity("uv", UnitView.class);
                if (!isAdmin) {
                    query.setInteger(0, userId);
                }
        return (List<UnitView>) query.list();
    }

    public List<UnitView> findByVehicleId(int vehicleId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT DISTINCT {uv.*} " +
                " FROM unit_view {uv} " +
                        " JOIN map_unit_view_unit muvu ON muvu.group_id = uv.id AND muvu.vehicle_id = ?").addEntity("uv", UnitView.class);

        return query.setInteger(0, vehicleId).list();
    }

    public void removeByVehicleAndGroupIds(int vehicleId, int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "delete from map_unit_view_unit where vehicle_id = ? and group_id = ?"
        );
        query.setInteger(0,vehicleId).setInteger(1,groupId).executeUpdate();
    }

    public void removeMapGVByGroupId(int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "delete from map_unit_view_unit where group_id = ?"
        );
        query.setInteger(0,groupId).executeUpdate();
    }

    public void saveMapVehicleVeiw(int vehicleId, int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery("insert into map_unit_view_unit (vehicle_id,group_id) values (?,?)");
        query.setInteger(0,vehicleId).setInteger(1,groupId).executeUpdate();
    }

    public List<UnitView> findAllByAccountIds(List accounts) {
        Session session = getSession();

        Query query = session.createQuery("from com.sp.model.UnitView as obj where obj.account.id in (:accountList)");
        query.setParameterList("accountList", accounts);
        return query.list();
    }

    public List<UnitView> findAllByUserIdAndAccount(int userId, List accounts) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT DISTINCT {uv.*} " +
                " FROM unit_view {uv} " +
                        " JOIN map_user_account mua ON mua.account_id = uv.account_id " +
                        " where mua.user_id = ? and uv.account_id in (:accountList)").addEntity("uv", UnitView.class);
        query.setParameterList("accountList", accounts);
        return query.setInteger(0, userId).list();
    }

    public void removeMapUnitAccountByUnitId(int unitId){
        Session session = getSession();
        Query query = session.createSQLQuery("delete from map_unit_account where unit_id = ?");
        query.setInteger(0,unitId).executeUpdate();
    }

    public UnitView findUnitViewByIdWithRestrictions(int userId, int unitViewId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {uv.*} " +
                " FROM unit_view {uv} " +
                        " JOIN map_unit_view_unit muvu ON muvu.group_id = uv.id " +
                        " JOIN unit u ON u.id = muvu.vehicle_id " +
                        " JOIN imei i ON i.unit_id = u.id " +
                        " JOIN license l ON l.id = i.license_id " +
                        " WHERE uv.id = ? AND u.is_deleted = 0" +
                        " AND CURDATE() BETWEEN l.activation_date AND l.end_date " +
                        " AND NOT EXISTS (SELECT NULL FROM user_unit_disabling uud " +
                                        " WHERE uud.unit_id = muvu.vehicle_id AND uud.user_id = ?)")
                .addEntity("uv", UnitView.class);
        return (UnitView) query.setInteger(0, unitViewId).setInteger(1, userId).uniqueResult();
    }

}
