package com.sp.dao;

import com.sp.dto.BaseMapSource;
import com.sp.model.Handheld;
import com.sp.model.HandheldIncomingLogRecord;
import com.sp.model.HandheldType;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: andrey
 */
public class HandheldDao extends BaseDao {
     public List<Handheld> findNonExpiredByHandheldViewId(int viewId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {h.*} FROM handheld {h} "
                        + " JOIN map_handheld_view mhv ON mhv.unit_id = h.id"
                        + " JOIN license l ON l.id = h.license_id"
                        + " WHERE  mhv.group_id=? AND h.is_deleted=0 AND CURDATE() BETWEEN l.activation_date AND l.end_date")
        .addEntity("h", Handheld.class);

        return query.setInteger(0, viewId).list();
    }

    public List<Handheld> findNonExpiredByHandheldViewIdRefreshed(int viewId, Date prevRequestedDate) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {h.*} FROM handheld {h} "
                        + " JOIN map_handheld_view mhv ON mhv.unit_id = h.id"
                        + " JOIN license l ON l.id = h.license_id"
                        + " WHERE  mhv.group_id=? AND h.timestamp>=? AND h.is_deleted=0 AND CURDATE() BETWEEN l.activation_date AND l.end_date")
        .addEntity("h", Handheld.class);

        return query.setInteger(0, viewId).setTimestamp(1,prevRequestedDate).list();
    }

     public List<Handheld> findByLicenseId(int licenseId) {
        Session session = getSession();
        return (List<Handheld>) session.createQuery("from com.sp.model.Handheld as obj where obj.licenseId = ?")
                .setInteger(0, licenseId).list();
    }

    public Map<Integer,BaseMapSource> findMapSourceInfoByTimestamp(Date date) {
        String sql = "select h.id,ma.account_id,r.map_key,r.map_password,ms.descr from handheld h " +
                "join map_handheld_account ma on h.id = ma.unit_id " +
                "join account a on a.id = ma.account_id " +
                "join reseller r on r.id = a.reseller_id " +
                "join map_source ms on ms.id = r.map_source_id ";
        if(date!=null){
            sql+="where h.timestamp>?";
        }
        Query query = getSession().createSQLQuery(sql);
        if(date!=null){
            query.setTimestamp(0,date);
        }
        Map<Integer,BaseMapSource> baseMapSourceMap = new HashMap<Integer,BaseMapSource>();
        List mapSourceInfoList = query.list();
        for(Object o : mapSourceInfoList){
            BaseMapSource baseMapSource = new BaseMapSource();
            baseMapSource.setAccountId(Integer.parseInt(((Object[])o)[1].toString()));
            baseMapSource.setMapKey(((Object[])o)[2].toString());
            baseMapSource.setMapPassword(((Object[])o)[3]!=null ? ((Object[])o)[3].toString() : null);
            baseMapSource.setMapSourceDescr(((Object[])o)[4].toString());
            baseMapSourceMap.put(Integer.parseInt(((Object[])o)[0].toString()),baseMapSource);
        }
        return baseMapSourceMap;
    }

    public List<HandheldType> findDt1HandheldTypes(){
        Session session = getSession();
        Query query = session.createSQLQuery("select {h.*} from handheld_type {h} where descr like 'Pawprint%'").addEntity("h", HandheldType.class);
        return (List<HandheldType>) query.list();
    }

    public List<HandheldType> findHandheldTypes(){
        Session session = getSession();
        Query query = session.createSQLQuery("select {h.*} from handheld_type {h} where descr not like 'Pawprint%'").addEntity("h", HandheldType.class);
        return (List<HandheldType>) query.list();
    }

    public List<HandheldIncomingLogRecord> findIncomingLogsByPeriodAndHandheldId(TimePeriod timePeriod, int handheldId){
        Criteria criteria = getSession().createCriteria(HandheldIncomingLogRecord.class);
        criteria.add(Restrictions.between("recDate", timePeriod.getStartDate(), timePeriod.getEndDate()));
        criteria.add(Restrictions.eq("handheldId", handheldId));
        criteria.add(Restrictions.ne("lat", 0d));
        criteria.add(Restrictions.ne("lon", 0d));
        criteria.addOrder(Order.asc("recDate"));
        return criteria.list();
    }

}
