package com.sp.dao;


/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 12.05.2009
 * Time: 11:48:23
 * To change this template use File | Settings | File Templates.
 */

import com.sp.model.AncillaryDevice;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class AncillaryDeviceDao extends BaseDao {
    
       public List<AncillaryDevice> findByUserId(int userId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {ad.*} " +
                " FROM ancillary_device {ad} " +
                        " JOIN map_user_account mua ON mua.account_id = ad.account_id AND mua.user_id = ?")
                .addEntity("ad", AncillaryDevice.class);

        return query.setInteger(0, userId).list();
    }
}
