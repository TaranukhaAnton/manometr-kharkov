package com.sp.dao;

import com.sp.model.BaseModel;
import com.sp.model.PhoneScheduleDetails;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 17.04.2009
 * Time: 11:29:38
 * To change this template use File | Settings | File Templates.
 */
public class PhoneScheduleDetailsDao extends BaseDao {
    public List<BaseModel> findByScheduleId(int scheduleId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select {psd.*} from phone_schedule_details {psd} " +
                        "where psd.schedule_id= ?").addEntity("psd", PhoneScheduleDetails.class);
        return (List<BaseModel>)query.setInteger(0,scheduleId).list();

    }
}
