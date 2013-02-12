package com.sp.dao;

import com.sp.SpContext;
import com.sp.model.*;
import com.sp.security.UserDetailsInfo;
import com.sp.util.Constants;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BaseDao  {

    @Autowired
    SessionFactory sessionFactory;

    private Map<Class, String> classToTableMap;

    public BaseDao() {
        classToTableMap = new HashMap<Class, String>();
        classToTableMap.put(SecurityUser.class, "security_user");
        classToTableMap.put(Vehicle.class, "unit");
        classToTableMap.put(TrackingDevice.class, "imei");
        classToTableMap.put(Phone.class, "phone_unit");
        classToTableMap.put(Handheld.class, "handheld");
        classToTableMap.put(ScheduleMailAlert.class, "schedule_mail_alert");
    }

    public void initAuditEvent(AuditEvent event) {
        initAuditEvent(event, 0);
    }

    public void initAuditEvent(AuditEvent event, int accountId) {
        Account account = null;
        Reseller reseller = null;
        if (accountId > 0) {
            account = (Account) findById(Account.class, accountId);
            reseller = account.getReseller();
        }
        UserDetailsInfo userDetailsInfo = SpContext.getUserDetailsInfo();
        SecurityUser securityUser = (SecurityUser) findById(SecurityUser.class, userDetailsInfo.getUserId());
        event.setAccount(account);

        event.setReseller(reseller);
        event.setSecurityUser(securityUser);
    }


    public void commitAndBeginNewTransaction() {
        getSession().getTransaction().commit();
        getSession().getTransaction().begin();
    }

    public void rollbackTransaction() {
        getSession().getTransaction().rollback();
    }

    public void flush() {
        getSession().flush();        
    }

    public BaseModel findById(Class clazz, int id) {
        Session session = getSession();
        return (BaseModel) session.get(clazz, id);
    }

    public List<BaseModel> findByIdCollection(Class clazz, Collection<Integer> idSet) {// use only top level model class. don't use MappedSuperclass
        if (idSet.size() == 0) {
            return null;
        } else {
            Criteria criteria = getSession().createCriteria(clazz);
            criteria.add(Restrictions.in("id", idSet));
            return criteria.list();
        }
    }

    public List<BaseModel> findAll(Class clazz) {
        return findAll(clazz, null);
    }

    public List<BaseModel> findAllBaseItemViewByUserId(Class clazz, int userId) {
            Session session = getSession();
        Query query = session.createQuery(
                " SELECT DISTINCT c " +
                        " FROM " + clazz.getName() + " c, " + MapUserAccount.class.getName() + " mua " +
                        "where mua.accountId = c.account.id AND mua.securityUser.id = ?");
        return query.setInteger(0, userId).list();
    }

    //phone_unit, map_phone_account
    public List<BaseModel> findAllMovableItemsByUserId(Class movableItemClazz, Class mapItemAccountClass, int userId, Boolean active) {
        Session session = getSession();
        Query query = session.createQuery(
                "SELECT distinct u " +
                        " FROM " + movableItemClazz.getName() + " u, " + mapItemAccountClass.getName() + " muacc, " + MapUserAccount.class.getName() +
                        " mua WHERE  muacc.unitId = u.id AND mua.accountId = muacc.accountId" +
                        " AND mua.securityUser.id = ?" +
                        (active == null ? "" : " and u.deleted=" + (active ? "0" : "1")));

        return query.setInteger(0, userId).list();
    }

    public List<BaseModel> findByParentId(Class clazz, int parentId) {
        Session session = getSession();
        return (List<BaseModel>) session.createQuery("from " + clazz.getName() + " c where c.parent.id = ?")
                .setInteger(0, parentId).list();
    }

    public List<BaseModel> findByAccountId(Class clazz, int accountId, boolean activeFilter) {
        Session session = getSession();
        return (List<BaseModel>) session.createQuery("from " + clazz.getName()
                + " c where c.account.id = ? and c.deleted=" + !activeFilter)
                .setInteger(0, accountId).list();
    }

    public List<BaseModel> findAll(Class clazz, Boolean active) {
        Session session = getSession();
        String order = clazz.getSuperclass().equals(EnumModel.class) ? "descr" : "id";

        return (List<BaseModel>) session.createQuery("from " + clazz.getName()
                + (active == null ? "" :
                active ? " where deleted = false" : " where deleted = true")
                + " order by " + order).list();
    }

    public List<BaseModel> findAll(Class clazz, int limit) {
        Session session = getSession();
        return (List<BaseModel>) session.createSQLQuery("select {c.*} from " + classToTableMap.get(clazz) + " {c} limit " + limit).addEntity("c", clazz).list();
    }

    public void saveDomain(BaseModel domainObj) {
        Session session = getSession();
        session.save(domainObj);
    }

    public void saveOrUpdateDomain(BaseModel domainObj) {
        Session session = getSession();
        session.saveOrUpdate(domainObj);
    }

    public void updateDomain(BaseModel domainObj) {
        Session session = getSession();
        session.update(domainObj);
    }

    public void removeDomain(BaseModel domainObj) {
        Session session = getSession();
        session.delete(domainObj);
    }

    public void markActiveInactive(Class clazz, int id, boolean active) {
        String tableName = classToTableMap.get(clazz);
        if (tableName == null) {
            String tokens[] = clazz.getName().split("\\.");
            tableName = tokens[tokens.length - 1].toLowerCase();
        }
        markActiveInactive(tableName, id, active);
    }

    private void markActiveInactive(String tableName, int id, boolean active) {
        Session session = getSession();
        Query query = session.createSQLQuery("UPDATE " + tableName
                + " SET is_deleted=" + (active ? "0" : "1")
                + " WHERE id=?").setInteger(0, id);
        query.executeUpdate();
    }

    public List findRootsByAccountIdAndClass(Class clazz, int accountId) {
        Session session = getSession();
        return session.createQuery("from " + clazz.getName() + "   as obj where obj.parent = null and obj.account.id = ?")
                .setInteger(0, accountId).list();
    }

    public BaseMovableItem findByImei(Class clazz, String imei) {
        Session session = getSession();
        return (BaseMovableItem) session.createQuery("from " + clazz.getName() + " as obj where obj.imei = ?").setString(0, imei).uniqueResult();
    }

    public List<BaseMapItemAccount> findMapItemAccountsByItemId(Class clazz, int id) {
        Session session = getSession();
        return session.createQuery("from " + clazz.getName() + " as obj where obj.unitId = ?")
                .setInteger(0, id).list();
    }

    //phone_view, map_phone_unit    findByPhoneId
    public List<AccountedMovableItemView> findAllBaseItemViewByItemId(Class viewClass, Class mapItemViewClass, int id) {
        Session session = getSession();
        Query query = session.createQuery("select c from " + viewClass.getName() + " c, " + mapItemViewClass.getName() +
                " jc where jc.groupId = c.id and jc.unitId = ?");
        return query.setInteger(0, id).list();
    }

    public List<AccountedMovableItemView> findAllBaseItemViewByAccountIds(Class clazz, List accounts) {
        Session session = getSession();
        Query query = session.createQuery("select distinct obj from " + clazz.getName() + " as obj where obj.account.id in (:accountList)");
        query.setParameterList("accountList", accounts);
        return (List<AccountedMovableItemView>) query.list();
    }

    public List<AccountedMovableItemView> findAllBaseItemViewByUserIdAndAccount(Class clazz, int userId, List accounts) {
        Session session = getSession();
        Query query = session.createQuery(
                "select v FROM " + clazz.getName() + " v, " + MapUserAccount.class.getName() + " mua " +
                        "where mua.accountId = v.account.id " +
                        " and mua.securityUser.id = ? and v.account.id in (:accountList)");
        query.setParameterList("accountList", accounts);
        return query.setInteger(0, userId).list();
    }

    public List<BaseMapItemAccount> findMapMovableItemAccountsByMovableItemId(Class clazz, int phoneId) {
        Session session = getSession();
        //com.sp.model.MapPhoneAccount
        return session.createQuery("from " + clazz.getName() + " as obj where obj.unitId = ?")
                .setInteger(0, phoneId).list();
    }

    public void removeBaseItemViewByItemId(Class clazz, int itemId) {
        Session session = getSession();
        Query query = session.createQuery("delete from " + clazz.getName() + " where unitId = ?");
        query.setInteger(0, itemId);
        query.executeUpdate();
    }

    public void removeBaseItemViewByItemGroupId(Class viewClass, int itemId, int groupId) {
        Session session = getSession();
        //map_phone_unit
        Query query = session.createQuery(
                "delete from " + viewClass.getName() + " where unitId = ? and groupId = ?"
        );
        query.setInteger(0, itemId).setInteger(1, groupId).executeUpdate();
    }

    public List findAllUnassignedGroups(Class clazz) {
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return session.createQuery("from " + clazz.getName() + " as obj where obj.descr like ?")
                .setString(0, unassignedStr).list();
    }

    public AccountedMovableItemView findUnassignedGroupByAccountId(Class viewClass, int accountId) {
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return (AccountedMovableItemView) session.createQuery("from " + viewClass.getName() + " as obj where obj.descr like ? and obj.account.id = ?")
                .setString(0, unassignedStr).setInteger(1, accountId).uniqueResult();
    }

    //map_phone_unit
    public int findCountMapsForUnassignedGroup(Class mapItemView, int groupId) {
        Session session = getSession();
        Query query = session.createQuery("select count(*) from " + mapItemView.getName() + " where groupId = ?");
        return Integer.parseInt(query.setInteger(0, groupId).uniqueResult().toString());
    }


    //map_phone_account
    public void removeMapMovableItemAccountByItemId(Class mapAccountClass, int itemId) {
        Session session = getSession();
        Query query = session.createQuery("delete from " + mapAccountClass.getName() + " where unitId = ?");
        query.setInteger(0, itemId).executeUpdate();
    }


    //map_phone_account
    public BaseMapItemAccount findMapByAccountAndItemIds(Class mapItemAccountClass, BaseMapItemAccount mpa) {
        Session session = getSession();
        Query query = session.createQuery("select mia from " + mapItemAccountClass.getName() + "  mia where accountId = ? and unitId = ?");
        return (BaseMapItemAccount) query.setInteger(0, mpa.getAccountId()).setInteger(1, mpa.getUnitId()).uniqueResult();
    }

    public List<AccountedMovableItemView> findBaseItemViewsByListIds(Class viewClass, List<Integer> groupList) {
        Session session = getSession();
        Query query = session.createQuery("from " + viewClass.getName() + " where id in (:idList)");
        return (List<AccountedMovableItemView>) query.setParameterList("idList", groupList).list();
    }

    public List<BaseIncomingLogRecord> findLogByClassPeriodAndNodeId(Class clazz, TimePeriod timePeriod, String nodeId) {
        Criteria criteria = getSession().createCriteria(clazz);
        criteria.add(Restrictions.between("recDate", timePeriod.getStartDate(), timePeriod.getEndDate()));
        criteria.add(Restrictions.eq("nodeId", nodeId));
        criteria.add(Restrictions.ne("lat", 0d));
        criteria.add(Restrictions.ne("lon", 0d));
        criteria.addOrder(Order.asc("recDate"));
        return criteria.list();
    }

    public List<BaseMovableItem> findMovableItemByAccountId(int accountId, Class itemClass, Class accountClass) {
        Session session = getSession();
        Query q = session.createQuery("select c from " +
                itemClass.getName() + " c, " + accountClass.getName() + " a" +
                " where c.id = a.unitId and a.accountId = ?");
        return (List<BaseMovableItem>) q.setInteger(0, accountId).list();
    }


    public EnumModel findByDescr(Class clazz, String descr) {
        Session session = getSession();
        return (EnumModel) session.createQuery("from " + clazz.getName() + " as obj where obj.descr = ?")
                .setString(0, descr).uniqueResult();
    }

    public EnumModel findByCode(Class clazz, int code) {
        Session session = getSession();
        return (EnumModel) session.createQuery("from " + clazz.getName() + " as obj where obj.code = ?")
                .setInteger(0, code).uniqueResult();
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
