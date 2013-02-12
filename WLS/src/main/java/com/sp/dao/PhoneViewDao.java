package com.sp.dao;

import com.sp.model.MapPhoneAccount;
import com.sp.model.PhoneView;
import com.sp.util.Constants;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ananda
 * Date: Mar 25, 2009
 * Time: 10:41:26 AM
 */
public class PhoneViewDao extends BaseDao {
     public List<PhoneView> findByParentId(int parentId) {
        Session session = getSession();
        return (List<PhoneView>) session.createQuery("from com.sp.model.PhoneView  as obj where obj.parent.id = ?").setInteger(0, parentId).list();
    }
   
    public List<PhoneView> findRootsByAccountId(int accountId) {
        Session session = getSession();
        return (List<PhoneView>) session.createQuery("from com.sp.model.PhoneView  as obj where obj.parent = null and obj.account.id = ?")
                .setInteger(0, accountId).list();
    }

    public PhoneView findUnassignedGroupByAccount(int accountId){
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return (PhoneView) session.createQuery("from com.sp.model.PhoneView as obj where obj.descr like ? and obj.account.id = ?")
                .setString(0, unassignedStr).setInteger(1,accountId).uniqueResult();
    }

    public List<PhoneView> findAllUnassignedGroups(){
        Session session = getSession();
        String unassignedStr = "%".concat(Constants.UNASSIGNED_GROUP).concat("%");
        return (List<PhoneView>) session.createQuery("from com.sp.model.PhoneView as obj where obj.descr like ?")
                .setString(0,unassignedStr).list();
    }

    public int findCountMapsForUnassignedGroup(int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery("select count(*) from map_phone_unit where group_id = ?");
        return Integer.parseInt(query.setInteger(0,groupId).uniqueResult().toString());
    }

    public List<PhoneView> findRootsByResellerId(int resellerId) {
        Session session = getSession();
        return (List<PhoneView>) session
            .createQuery("from com.sp.model.PhoneView  as obj where obj.parent = null and obj.account.reseller.id = ?")
                .setInteger(0, resellerId).list();
    //join com.sp.model.Account acc join com.sp.model.Reseller res
    }

    public List<PhoneView> findAllByUserId(int userId) {
            Session session = getSession();

            Query query = session.createSQLQuery(
                    "SELECT DISTINCT {pv.*} " +
                    " FROM phone_view {pv} " +
                            " JOIN map_user_account mua ON mua.account_id = pv.account_id AND mua.user_id = ?").addEntity("pv", PhoneView.class);

            return query.setInteger(0, userId).list();
        }
    


    public List<PhoneView> findAllByAccountIds(List accounts) {
        Session session = getSession();

        Query query = session.createQuery("from com.sp.model.PhoneView as obj where obj.account.id in (:accountList)");
        query.setParameterList("accountList", accounts);
        return query.list();
    }



    public List<PhoneView> findAllByUserIdAndAccount(int userId, List accounts) {
        Session session = getSession();
         Query query = session.createSQLQuery(
                "SELECT DISTINCT {pv.*} " +
                " FROM phone_view {pv} " +
                        " JOIN map_user_account mua ON mua.account_id = pv.account_id " +
                        " where mua.user_id = ? and pv.account_id in (:accountList)").addEntity("pv", PhoneView.class);
        query.setParameterList("accountList", accounts);
        return query.setInteger(0, userId).list();
    }

    public void removeByPhoneGroupIds(int phoneId, int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "delete from map_phone_unit where phone_id = ? and group_id = ?"
        );
        query.setInteger(0,phoneId).setInteger(1,groupId).executeUpdate();
    }

    public void saveMapPhoneVeiw(int phoneId, int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery("insert into map_phone_unit (phone_id,group_id) values (?,?)");
        query.setInteger(0,phoneId).setInteger(1,groupId).executeUpdate();
    }

    
    public List<PhoneView> findByPhoneId(int phoneId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT DISTINCT {pv.*} " +
                " FROM phone_view {pv} " +
                        " JOIN map_phone_unit muvu ON muvu.group_id = pv.id AND muvu.phone_id = ?").addEntity("pv", PhoneView.class);

        return query.setInteger(0, phoneId).list();
    }

    

    public MapPhoneAccount findMapByAccountAndPhoneIds(MapPhoneAccount mpa){
        Session session = getSession();
        Query query = session.createSQLQuery("select {mpa.*} from map_phone_account {mpa} where account_id = ? and unit_id = ?").addEntity("mpa",MapPhoneAccount.class);
        return (MapPhoneAccount)query.setInteger(0,mpa.getAccountId()).setInteger(1,mpa.getphoneId()).uniqueResult();
    }

    public void removeMapPhoneAccountByPhoneId(int phoneId){
        Session session = getSession();
        Query query = session.createSQLQuery("delete from map_phone_account where unit_id = ?");
        query.setInteger(0,phoneId).executeUpdate();
    }
}
