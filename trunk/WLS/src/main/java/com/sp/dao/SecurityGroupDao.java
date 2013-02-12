package com.sp.dao;

import com.sp.model.SecurityGroup;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class SecurityGroupDao extends BaseDao {
    public List<SecurityGroup> findByParentId(int parentId) {
        Session session = getSession();
        return (List<SecurityGroup>) session.createQuery("from com.sp.model.SecurityGroup  as obj where obj.parent.id = ?").setInteger(0, parentId).list();
    }    
}