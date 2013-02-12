package com.sp.dao;

import com.sp.model.GroupPermissionGranting;
import com.sp.model.PermissionTypeFilter;
import com.sp.model.UserPermissionGranting;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class PermissionGrantingDao extends BaseDao {
    public List<UserPermissionGranting> findByUserId(int userId, PermissionTypeFilter type) {
        Session session = getSession();

        String sql = "SELECT {gup.*} FROM granted_user_permission {gup} " +
                " JOIN permission p ON p.id = gup.permission_id" +
                " WHERE gup.user_id = ? ";

        if(type != null)
        {
        	sql += " AND p.category LIKE '"+type.name()+"'";
        }

        sql += " ORDER BY p.descr";

        return (List<UserPermissionGranting>) session.createSQLQuery(sql)
                .addEntity("gup", UserPermissionGranting.class)
                .setInteger(0, userId)
                .list();
    }

    public UserPermissionGranting findUserPermissionGranting(int userId, int permissionId) {
        Session session = getSession();
        return (UserPermissionGranting) session.createQuery("from com.sp.model.UserPermissionGranting as obj where obj.userId = ? and obj.permissionId = ?")
                .setInteger(0, userId).setInteger(1, permissionId).uniqueResult();
    }

    public UserPermissionGranting findUserPermissionGranting(int userId, String permissionDescr, String permissionAction) {
        Session session = getSession();

        return (UserPermissionGranting) session.createSQLQuery("SELECT {up.*} FROM granted_user_permission {up}" +
                                                                " WHERE user_id = ? AND permission_id = (SELECT id FROM permission WHERE descr = ? AND object_action = ?)")
                .addEntity("up", UserPermissionGranting.class).setInteger(0, userId).setString(1, permissionDescr).setString(2, permissionAction).uniqueResult();
    }

    public List<GroupPermissionGranting> findByGroupId(int groupId) {
        Session session = getSession();

        return (List<GroupPermissionGranting>) session.createQuery("from com.sp.model.GroupPermissionGranting  as obj where obj.groupId = ? ")
                .setInteger(0, groupId)
                .list();
    }

    public GroupPermissionGranting findGroupPermissionGranting(int groupId, int permissionId) {
        Session session = getSession();
        return (GroupPermissionGranting) session.createQuery("from com.sp.model.GroupPermissionGranting as obj where obj.groupId = ? and obj.permissionId = ?")
                .setInteger(0, groupId).setInteger(1, permissionId).uniqueResult();
    }

    public List<GroupPermissionGranting> findGroupPermissionGrantingsForUser(int userId, String permissionDescr, String permissionAction) {
        Session session = getSession();
        return (List<GroupPermissionGranting>) session.createSQLQuery(
                "SELECT {gp.*} " +
                "FROM granted_group_permission {gp}" +                    
                    " JOIN map_user_group mug ON mug.group_id = gp.group_id" +
                " WHERE mug.user_id = ? AND permission_id = (SELECT id FROM permission WHERE descr = ? AND object_action = ?)")
                .addEntity("gp", GroupPermissionGranting.class).setInteger(0, userId).setString(1, permissionDescr).setString(2, permissionAction).list();
    }

    public List<GroupPermissionGranting> findGroupPermissionGrantingsForUser(int userId) {
        Session session = getSession();
        return (List<GroupPermissionGranting>) session.createSQLQuery(
                "SELECT {gp.*} " +
                "FROM granted_group_permission {gp}" +
                    " JOIN map_user_group mug ON mug.group_id = gp.group_id" +
                " WHERE mug.user_id = ?")
                .addEntity("gp", GroupPermissionGranting.class).setInteger(0, userId).list();
    }
}
