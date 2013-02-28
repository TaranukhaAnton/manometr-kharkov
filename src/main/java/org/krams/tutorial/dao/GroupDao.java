package org.krams.tutorial.dao;

import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.krams.tutorial.domain.UnitView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class GroupDao {
    @Autowired
    SessionFactory sessionFactory;

    public List<UnitView> findGroups(int userId, boolean isAdmin) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createSQLQuery(
                "SELECT distinct {uv.*} " +
                        " FROM unit_view {uv} " +
                        " JOIN map_user_account mua ON mua.account_id = uv.account_id" +
                        (isAdmin ? " " : " AND mua.user_id = ?") +
                        " JOIN account a on a.id = mua.account_id " +
                        " WHERE a.is_deleted = 0" +
                        (isAdmin ? " " : " AND a.is_ios_application_allowed = 1"))
                .addEntity("uv", UnitView.class);
        if (!isAdmin) {
            query.setInteger(0, userId);
        }
        return (List<UnitView>) query.list();
    }


    public UnitView findById(int id, boolean initVehicles) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UnitView.class);
          criteria.add(Restrictions.idEq(id));

        UnitView unitView = (UnitView)criteria.uniqueResult();
        if (initVehicles){
            Hibernate.initialize(unitView.getGroupVehicles());
        }
        return unitView;
    }



}
