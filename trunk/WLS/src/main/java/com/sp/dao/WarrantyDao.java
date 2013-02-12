package com.sp.dao;

import com.sp.model.Warranty;
import com.sp.service.WarrantyService;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class WarrantyDao extends BaseDao {
    public List<Warranty> findByFilter(int filter, Boolean active) {
        Session session = getSession();

        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        switch(filter) {
            case WarrantyService.FILTER_TYPE_CURRENT:
                return session.createQuery("from com.sp.model.Warranty as obj where ? >= obj.startDate and ? <= obj.endDate and obj.deleted = ?")
                        .setDate(0, curDate).setDate(1, curDate).setBoolean(2, !active).list();
            case WarrantyService.FILTER_TYPE_EXPIRED_IN_6_LAST_MONTHS:
                calendar.add(Calendar.MONTH, -6);
                Date fromDate = calendar.getTime();

                return session.createQuery("from com.sp.model.Warranty as obj where obj.endDate >= ? and obj.endDate <= ? and obj.deleted = ?")
                        .setDate(0, fromDate).setDate(1, curDate).setBoolean(2, !active).list();
            case WarrantyService.FILTER_TYPE_WILL_EXPIRE_IN_3_NEXT_MONTHS:                
                calendar.add(Calendar.MONTH, 3);
                Date toDate = calendar.getTime();

                return session.createQuery("from com.sp.model.Warranty as obj where obj.endDate >= ? and obj.endDate <= ? and obj.deleted = ?")
                        .setDate(0, curDate).setDate(1, toDate).setBoolean(2, !active).list();
            default:
                throw new RuntimeException("Unknown filter=" + filter);
        }
    }
}
