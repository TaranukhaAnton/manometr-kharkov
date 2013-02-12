package com.sp.dao;

import com.sp.model.Permission;
import com.sp.model.PermissionTypeFilter;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class PermissionDao extends BaseDao {
    public List<Permission> findByFilter(PermissionTypeFilter permissionTypeFilter) {
        Session session = getSession();
        String sql = "SELECT {p.*} FROM permission {p}";

        if (permissionTypeFilter != null) {
            sql += " WHERE category = ?";
        }
        Query query = session.createSQLQuery(sql).addEntity("p", Permission.class);
        if (permissionTypeFilter != null) {
            query.setString(0, permissionTypeFilter.toString());
        }

        sql += " ORDER BY p.descr";

        return (List<Permission>) query.list();
    }

    public List<Permission> findHeaderReports() {
        Session session = getSession();
        String sql = "SELECT {p.*} FROM permission {p} where p.is_header_report = 1 order by p.category desc, p.descr asc";
        Query query = session.createSQLQuery(sql).addEntity("p", Permission.class);
        return (List<Permission>) query.list();
    }
}
