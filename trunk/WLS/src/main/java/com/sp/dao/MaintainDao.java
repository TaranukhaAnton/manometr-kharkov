package com.sp.dao;

import com.sp.model.Maintain;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class MaintainDao extends BaseDao {
    public List<Maintain> findByTrackingDeviceId(int trackingDeviceId) {
        Session session = getSession();
        return (List<Maintain>) session.createQuery("from com.sp.model.Maintain  as obj where obj.trackingDevice.id = ?")
                .setInteger(0, trackingDeviceId).list();
    }
}
