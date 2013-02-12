package com.sp.dao;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Created by IntelliJ IDEA.
 * Date: 20.11.2009
 * Time: 15:22:52
 * To change this template use File | Settings | File Templates.
 */
public class UserFeedbackDao extends BaseDao {
    public void save(int userId,String url,int groupId, String comment){
        Session session = getSession();
        Query query = session.createSQLQuery("insert into user_feedback (user_id,url,vehicle_group_id,comment) values (?,?,?,?)");
        query.setInteger(0,userId).setString(1,url).setInteger(2,groupId).setString(3,comment).executeUpdate();
    }
}
