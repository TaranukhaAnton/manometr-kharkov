package com.sp.dao.transformer;

import com.sp.SpContext;
import com.sp.model.BaseTrackingDevice;
import com.sp.model.Journey;
import com.sp.util.Util;
import org.hibernate.transform.ResultTransformer;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Oleg
 * Date: 30.10.12
 * Time: 10:46
 * Filter journey list to remove daylight saving entries
 */
public class JourneyDayLightListTransformer implements ResultTransformer {

    private Date startDate;
    private Date endDate;

    private static final int MILLIS_IN_HOUR = 3600 * 1000;

    public JourneyDayLightListTransformer(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public Object transformTuple(Object[] objects, String[] strings) {
        return objects[0];
    }

    @Override
    public List transformList(List list) {
        Iterator i = list.iterator();
        while(i.hasNext()) {
            Journey j = (Journey) i.next();
            BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(j.getVehicle().getId());
            if (Util.inDaylightTimeUK(j.getStartDate()) && trackingDevice != null) {
                if ((trackingDevice.isDaylightSaving() && isJourneyInFirstHour(j.getStartDate())) || (!trackingDevice.isDaylightSaving() && isJourneyInLastHour(j.getStartDate()))) {
                    i.remove();
                }
            }

        }
        return list;
    }

    private boolean isJourneyInFirstHour(Date date) {
        return date.getTime() - startDate.getTime() < MILLIS_IN_HOUR;
    }

    private boolean isJourneyInLastHour(Date date) {
        return endDate.getTime() - date.getTime() < MILLIS_IN_HOUR;
    }



}
