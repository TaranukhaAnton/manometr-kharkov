package com.sp.service;

import com.sp.SpContext;
import com.sp.dao.BaseDao;
import com.sp.dao.UnitViewDao;
import com.sp.dao.UserDao;
import com.sp.dao.VehicleDao;
import com.sp.exception.ServiceException;
import com.sp.model.*;
import com.sp.util.Constants;
import com.sp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class UnitViewServiceImpl implements UnitViewService {
    private final static Logger LOGGER = Logger.getLogger(UnitViewServiceImpl.class);

    @Autowired
    private UnitViewDao unitViewDao;

    @Autowired
    private BaseDao baseDao;

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private UserDao userDao;


    @Override
    public List<UnitView> getByParentId(int parentId) {
        LOGGER.info("getByParentId() started, parentId=" + parentId);
        List<UnitView> objList = unitViewDao.findByParentId(parentId);
        List<UnitView> res = new ArrayList<UnitView>(objList.size());
        for (UnitView obj : objList) {
            UnitView copy = new UnitView();
            obj.copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getByParentId() finished");
        return res;
    }

    @Override
    public List<BaseMovableItemView> getBaseItemtRootsByAccountId(int accountId) {
        LOGGER.info("getBaseItemRootsByAccountId() started, accountId=" + accountId);
        List<UnitView> objList = unitViewDao.findRootsByAccountId(SpContext.getUserDetailsInfo().getUserId(),
                accountId);
        List<BaseMovableItemView> res = new ArrayList<BaseMovableItemView>(objList.size());
        for (UnitView obj : objList) {
            UnitView copy = new UnitView();
            obj.copyTo(copy);
            res.add(copy);
        }

        LOGGER.info("getBaseItemRootsByAccountId() finished");
        return res;
    }

    @Override
    public List<UnitView> getByVehicleId(int vehicleId) {
        LOGGER.info("getByVehicleId() started, vehicleId=" + vehicleId);
        List<UnitView> objList = unitViewDao.findByVehicleId(vehicleId);
        List<UnitView> res = new ArrayList<UnitView>(objList.size());
        for (UnitView obj : objList) {
            UnitView copy = new UnitView();
            obj.copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getByVehicleId() finished");
        return res;
    }



    private void saveAllAccountsByGroups(int vehicleId, List<UnitView> allGroupsByVehicle){
        for (UnitView unitView : allGroupsByVehicle){
            MapUnitAccount mua = new MapUnitAccount();
            mua.setVrehicleId(vehicleId);
            mua.setAccountId(unitView.getAccount().getId());
            if (!isExistsMapUnitAccount(mua)){
                baseDao.saveDomain(mua);
            }
        }
    }

    private boolean isExistsMapUnitAccount(MapUnitAccount mua){
        MapUnitAccount existedMUA= vehicleDao.findMapByAccountAndVehicleIds(mua);
        return existedMUA != null;
    }

    @Override
    public Integer getLastUnitViewByUnitId(int userId){
        return userDao.findPrefsByUserId(userId).getLastUnitViewId();
    }


    @Override
    public void save(UnitView newObj) {
        LOGGER.info("save() started, newObj.id=" + newObj.getId());
        UnitView oldObj;
        if (newObj.getId() == 0) {
            oldObj = newObj;
        }
        else {
            oldObj = (UnitView) baseDao.findById(UnitView.class, newObj.getId());
            newObj.copyTo(oldObj);
        }

        baseDao.saveDomain(oldObj);
    
        if (!Util.isUnassignedGroup(oldObj)){
            reassignVehiclesFromUnassignedGroups(oldObj);
        }
        setNewAccountToChildGroups(oldObj);
        saveMapAccountUnitList(oldObj);
        LOGGER.info("save() finished");
    }



    private void reassignVehiclesFromUnassignedGroups(UnitView unitView){
        List<UnitView>  unassignedGroups = unitViewDao.findAllUnassignedGroups();
        if (unassignedGroups != null && unitView.getGroupVehicles() != null){
            for (UnitView unassigned : unassignedGroups){
                for (Vehicle vehicle : unitView.getGroupVehicles()){
                    if (Util.isModelInCollection(vehicle, unassigned.getGroupVehicles())){
                        unitViewDao.removeByVehicleAndGroupIds(vehicle.getId(),unassigned.getId());
                        saveMapAccountUnitIdRemoveOldGroup(vehicle.getId(),unassigned);
                    }
                }
                if (unitViewDao.findCountMapsForUnassignedGroup(unassigned.getId()) == 0){
                    baseDao.removeDomain(baseDao.findById(UnitView.class, unassigned.getId()));
                }
            }
        }
    }
              
    @Override
    public void deleteAllEmptyUnassignedGroups(){
        LOGGER.info("deleteAllEmptyUnassignedGroups() started");
         List<UnitView>  unassignedGroups = unitViewDao.findAllUnassignedGroups();
         if (unassignedGroups != null){
            List<UnitView>  groupsForDel = new ArrayList<UnitView>();
            for (UnitView unassigned : unassignedGroups){
               if (unitViewDao.findCountMapsForUnassignedGroup(unassigned.getId()) == 0){
                   groupsForDel.add(unassigned);
               }
            }
            for (UnitView group: groupsForDel){
                baseDao.removeDomain(group);
            }
         }
        LOGGER.info("deleteAllEmptyUnassignedGroups() finished");
    }

    private void saveMapAccountUnitIdAddNewGroup(int vehicleId, UnitView unitView){
        unitViewDao.removeMapUnitAccountByUnitId(vehicleId);
        List<UnitView> allGroupsByVehicle = unitViewDao.findByVehicleId(vehicleId);
        allGroupsByVehicle = Util.addModelInListIfNotExists(unitView, allGroupsByVehicle);
        saveAllAccountsByGroups(vehicleId,allGroupsByVehicle);
    }

    private void saveMapAccountUnitIdRemoveOldGroup(int vehicleId, UnitView deletedUnitView){
        unitViewDao.removeMapUnitAccountByUnitId(vehicleId);
        List<UnitView> allGroupsByVehicle = unitViewDao.findByVehicleId(vehicleId);
        allGroupsByVehicle = Util.removeModelFromListIfExists(deletedUnitView, allGroupsByVehicle);
        saveAllAccountsByGroups(vehicleId,allGroupsByVehicle);
    }

    private void saveMapAccountUnitList(UnitView unitView){
        if (unitView.getGroupVehicles() != null){
            for (Vehicle vehicle : unitView.getGroupVehicles()){
                 saveMapAccountUnitIdAddNewGroup(vehicle.getId(),unitView);
            }
        }    
    }

    private void setNewAccountToChildGroups(UnitView unitView){
        List<UnitView> children = getByParentId(unitView.getId());
        for (UnitView childView : children){
            UnitView oldChild = (UnitView) baseDao.findById(UnitView.class, childView.getId());
            oldChild.setAccount(unitView.getAccount());
            baseDao.saveDomain(oldChild);
            saveMapAccountUnitList(oldChild);
        }
    }

    @Override
    public AccountedMovableItemView getBaseItemById(Integer id){
        if (id == null) {
            return null;
        }
        LOGGER.info("getBaseItemById() started");
        UnitView group = null;
        if (SpContext.getUserDetailsInfo().getRestrictedVehicleIdsCommaSeparated().length() == 0) {
            group = (UnitView) baseDao.findById(UnitView.class, id);
        } else {
            group = unitViewDao.findUnitViewByIdWithRestrictions(SpContext.getUserDetailsInfo().getUserId(), id);
        }
        AccountedMovableItemView result = null;
        if (group != null) {
            result = new AccountedMovableItemView();
            group.copyTo(result);
        }
        LOGGER.info("getBaseItemById() finished");
        return result;
    }

    @Override
    public AccountedMovableItemView getBaseItemByIdWithoutDisabledVehicles(Integer id){
        if (id == null) {
            return null;
        }
        LOGGER.info("getBaseItemById() started");
        UnitView group = (UnitView) baseDao.findById(UnitView.class, id);
        AccountedMovableItemView result = null;
        if (group != null) {
            result = new AccountedMovableItemView();
            group.copyTo(result);
        }
        LOGGER.info("getBaseItemById() finished");
        return result;
    }

    @Override
    public UnitView getById(int id) {
        LOGGER.info("getById() started, id=" + id);
        UnitView group = (UnitView) baseDao.findById(UnitView.class, id);
        UnitView copy = null;

        if (group != null) {
            copy = new UnitView();
            group.copyTo(copy);
        }
        LOGGER.info("getById() finished");
        return copy;
    }

    @Override
    public void deleteGroupCreateUnassigned(int deleteId){
        LOGGER.info("deleteGroupCreateUnassignedAction() started, id=" + deleteId);
        UnitView unitViewToDelete = (UnitView) baseDao.findById(UnitView.class, deleteId);
        Set<Vehicle> vehiclesToUnassignedGroup = getVehiclesToUnassignedGroup(unitViewToDelete);
        saveUnassignedGroupForVehicle(unitViewToDelete, vehiclesToUnassignedGroup);
        Set<Vehicle> vehiclesExceptToUnassignedGroup = getVehiclesExceptToUnassignedGroup(vehiclesToUnassignedGroup,unitViewToDelete);
        assignVehiclesExceptToUnassignedGroup(vehiclesExceptToUnassignedGroup, unitViewToDelete);
        baseDao.removeDomain(unitViewToDelete);
        LOGGER.info("deleteGroupCreateUnassignedAction() finished");
    }

    private void deleteGroupWithoutCreatingUnassigned(UnitView unitView){
        baseDao.removeDomain(baseDao.findById(UnitView.class, unitView.getId()));
        assignVehiclesExceptToUnassignedGroup(unitView.getGroupVehicles(),unitView);
    }

    private void assignVehiclesExceptToUnassignedGroup(Set<Vehicle> vehiclesExceptToUnassignedGroup, UnitView deletedUnitView){
        for (Vehicle vehicle : vehiclesExceptToUnassignedGroup){
           saveMapAccountUnitIdRemoveOldGroup(vehicle.getId(),deletedUnitView);
        }
    }



    private Set<Vehicle> getVehiclesExceptToUnassignedGroup(Set<Vehicle> vehiclesToUnassignedGroup, UnitView unitView){
        Set<Vehicle> allVehiclesInGroup = unitView.getGroupVehicles();
        for (Vehicle vehicle : vehiclesToUnassignedGroup){
            allVehiclesInGroup.remove(vehicle);
        }
        return allVehiclesInGroup;
    }

    private void saveUnassignedGroupForVehicle(UnitView unitView, Set<Vehicle> vehiclesToUnassignedGroup){
        UnitView unassignedGroup = getUnassignedGroupInAccount(unitView.getAccount().getId());
        if (unassignedGroup ==null){
            UnitView unassignedView = new UnitView();
            unassignedView.setAccount(unitView.getAccount());
            unassignedView.setDescr(Constants.UNASSIGNED_GROUP.concat(" - ").concat(unitView.getAccount().getDescr()));
            unassignedView.setCreatedDate(new Date());
            unassignedView.setNotes(Constants.UNASSIGNED_GROUP.concat(" Group In ").concat(unitView.getAccount().getDescr()).concat(" Account"));
            unassignedView.setGroupVehicles(vehiclesToUnassignedGroup);
            save(unassignedView);
        }else{
            unassignedGroup.getGroupVehicles().addAll(vehiclesToUnassignedGroup);
            save(unassignedGroup);
        }

    }


    private UnitView getUnassignedGroupInAccount(int accountId){
        UnitView unitView = unitViewDao.findUnassignedGroupByAccount(accountId);
        if (unitView == null){
            return null;
        }
        UnitView resUnit = new UnitView();
        unitView.copyTo(resUnit);
        return resUnit;
    }

    @Override
    public void delete(int id) throws ServiceException {
        LOGGER.info("delete() started, id=" + id);
        int childrenCnt = unitViewDao.findByParentId(id).size();
        if (childrenCnt > 0) {
            String chilStr = childrenCnt > 1 ? " children" : " child";
            throw new ServiceException("This group cannot be deleted because it has " + childrenCnt
                    + chilStr);
        }

        UnitView unitViewToDelete = (UnitView) baseDao.findById(UnitView.class, id);

        if (isExistsVehiclesOnlyInThisGroup(unitViewToDelete)){
            throw new ServiceException("vehicle");
        }
        deleteGroupWithoutCreatingUnassigned(unitViewToDelete);
        LOGGER.info("delete() finished");
    }

    private boolean isExistsVehiclesOnlyInThisGroup(UnitView unitView){
        if (unitView.getGroupVehicles() != null){
            for (Vehicle vehicle : unitView.getGroupVehicles()){
                List<UnitView> vehiclesGroups = unitViewDao.findByVehicleId(vehicle.getId());
                if (vehiclesGroups.size() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    private Set<Vehicle> getVehiclesToUnassignedGroup(UnitView unitView){
        Set<Vehicle> vehiclesToUnassignedGroup = new HashSet<Vehicle>();
        if (unitView.getGroupVehicles() != null){
            for (Vehicle vehicle : unitView.getGroupVehicles()){
                List<UnitView> vehiclesGroups = unitViewDao.findByVehicleId(vehicle.getId());
                if (vehiclesGroups.size() == 0){
                    vehiclesToUnassignedGroup.add(vehicle);
                }
            }
        }
        return vehiclesToUnassignedGroup;
    }

     @Override
     public List<UnitView> getAllByUserIdAndAccount(List<Integer> accounts) {
        LOGGER.info("getAllByUserIdAndAccount() started");
        List objList;
         if (SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            objList = unitViewDao.findAllByAccountIds(accounts);
        }
        else {
             int userId = SpContext.getUserDetailsInfo().getUserId();
            objList = unitViewDao.findAllByUserIdAndAccount(userId,accounts);
        }
        List<UnitView> res = new ArrayList<UnitView>(objList.size());
        for (Object obj : objList) {
            UnitView copy = new UnitView();
            ((UnitView)obj).copyTo(copy);
            res.add(copy);
        }

        LOGGER.info("getAllByUserIdAndAccount() finished");
        return res;
    }


    @Override
    public List<UnitView> getAllByUserId() {
        LOGGER.info("getAllByUserId() finished");
        List objList;
        if (SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            objList = baseDao.findAll(UnitView.class);
        }
        else {
            int userId = SpContext.getUserDetailsInfo().getUserId();
            objList = unitViewDao.findAllByUserId(userId);
        }
        List<UnitView> res = new ArrayList<UnitView>(objList.size());
        for (Object obj : objList) {
            UnitView copy = new UnitView();
            ((UnitView)obj).copyTo(copy);
            res.add(copy);
        }

        LOGGER.info("getAllByUserId() finished");
        return res;
    }

    @Override
    public List<UnitView> getAllByUserIdWithRestrictions(String restricted) {
        LOGGER.info("getAllByUserId() finished");
        List objList;

        objList = unitViewDao.findUnitViewsMobile(SpContext.getUserDetailsInfo().getUserId(), SpContext.getUserDetailsInfo().isUserAdmin());

        List<UnitView> res = new ArrayList<UnitView>(objList.size());
        for (Object obj : objList) {
            UnitView copy = new UnitView();
            ((UnitView)obj).copyTo(copy);
            res.add(copy);
        }

        LOGGER.info("getAllByUserId() finished");
        return res;
    }
}
