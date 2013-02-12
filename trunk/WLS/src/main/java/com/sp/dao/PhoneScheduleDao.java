package com.sp.dao;

import com.sp.model.MapSchedulePhone;
import com.sp.model.PhoneSchedule;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 16.04.2009
 * Time: 11:22:14
 * To change this template use File | Settings | File Templates.
 */
public class PhoneScheduleDao extends BaseDao {
    public List<PhoneSchedule> findByUserId(int userId){
        Session session = getSession();

        Query query = session.createSQLQuery(
                "select {ps.*} from phone_schedule {ps} " +
                        "where ps.user_id = ?").addEntity("ps", PhoneSchedule.class);
        return query.setInteger(0,userId).list();
    }

    public MapSchedulePhone findByPhoneAndScheduleIds(int phoneId, int scheduleId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select {msp.*} from map_schedule_phone {msp} " +
                "where msp.schedule_id=? and msp.phone_id=?").addEntity("msp", MapSchedulePhone.class);
        return (MapSchedulePhone)query.setInteger(0,scheduleId).setInteger(1,phoneId);
    }
    public MapSchedulePhone findByPhoneId(int phoneId){
        Session session = getSession();
        return (MapSchedulePhone) session.createQuery("from com.sp.model.MapSchedulePhone as obj where obj.phoneId = ?").setInteger(0,phoneId).uniqueResult();
    }

    public List<MapSchedulePhone> findAllByScheduleId(int scheduleId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select {msp.*} from map_schedule_phone {msp} " +
                "where msp.schedule_id=?").addEntity("msp", MapSchedulePhone.class);
        
        return (List<MapSchedulePhone>)query.setInteger(0,scheduleId).list();
    }

//    public void updateMapSchedulePhoneToNewSchedule(){
//
//    }

}
