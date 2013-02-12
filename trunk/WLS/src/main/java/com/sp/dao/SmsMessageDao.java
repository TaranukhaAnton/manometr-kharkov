package com.sp.dao;

import com.sp.model.SmsMessage;
import com.sp.model.SmsMessageStatus;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by Alexander
 */
public class SmsMessageDao extends BaseDao {

    public List<SmsMessage> findAllSmsMessageList(TimePeriod timePeriod){
        Criteria criteria = getSession().createCriteria(SmsMessage.class);
        criteria.add(Restrictions.between("sentDate", timePeriod.getStartDate(), timePeriod.getEndDate()));
        criteria.addOrder(Order.desc("sentDate"));
        return criteria.list();
    }

    public List<SmsMessage> findSmsMessageListForUser(TimePeriod timePeriod, int userId){
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {s.*} " +
                " FROM sms_messages {s} " +
                    " JOIN map_user_account mua ON mua.account_id = s.account_id" +
                    " WHERE sent_date BETWEEN ? AND ? AND mua.user_id = ?" +
                    " ORDER BY sent_date desc")
                .addEntity("s", SmsMessage.class);

        return query.setTimestamp(0,timePeriod.getStartDate()).setTimestamp(1,timePeriod.getEndDate()).setInteger(2, userId).list();
    }

    public SmsMessageStatus findSmsMessageStatusByFastSmsId(int fastSmsId){
        Criteria criteria = getSession().createCriteria(SmsMessageStatus.class);
        criteria.add(Restrictions.eq("fastSmsMessageId", fastSmsId));
        return (SmsMessageStatus)criteria.uniqueResult();
    }

    public List<SmsMessageStatus> findSmsMessageStatusListByMessageId(int messageId){
        Criteria criteria = getSession().createCriteria(SmsMessageStatus.class);
        criteria.add(Restrictions.eq("smsMessageId", messageId));
        return (List<SmsMessageStatus>)criteria.list();
    }

    public List<SmsMessage> findInProgressMessageList(){
        List<String> statusList = new ArrayList<String>(2);
        statusList.add("SENDING");
        statusList.add("SENT");
        Criteria criteria = getSession().createCriteria(SmsMessage.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.WEEK_OF_YEAR, -2);
        criteria.add(Restrictions.in("status", statusList));
        criteria.add(Restrictions.gt("sentDate", calendar.getTime()));
        return criteria.list();
    }

    public List findSmsMonthleyRecordList(int year, int partnerId, Integer userId){
        Session session = getSession();
        Query query = session.createSQLQuery("select a.id,a.descr,month(sent_date) as month,count(*) as sms_count from sms_messages s" +
                " join account a on a.id = s.account_id" +
                " join reseller r on r.id = a.reseller_id" +
                (userId!=null ? " join map_user_account mua ON mua.account_id = a.id AND mua.user_id = ?" : "") +
                " where a.is_deleted = false and year(sent_date) = ? and r.id = ? group by s.account_id,month(s.sent_date)");
        if(userId!=null){
            query.setInteger(0,userId).setInteger(1,year).setInteger(2,partnerId);
        }else{
            query.setInteger(0,year).setInteger(1,partnerId);
        }
        return query.list();
    }

}
