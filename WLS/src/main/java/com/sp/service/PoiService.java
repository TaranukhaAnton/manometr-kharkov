package com.sp.service;

import com.sp.SpContext;
import com.sp.dao.BaseDao;
import com.sp.dao.PoiDao;
import com.sp.dto.flex.PoiDto;
import com.sp.exception.PermissionException;
import com.sp.exception.ServiceException;
import com.sp.model.Account;
import com.sp.model.BaseModel;
import com.sp.model.Poi;
import com.sp.util.Constants;
import com.sp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class PoiService {

    private static final String POI_PERM_DESCR = "Poi";
    private final static Logger LOGGER = Logger.getLogger(PoiService.class);

    @Autowired
    BaseDao baseDao;

    @Autowired
    PoiDao poiDao;


    @Transactional
    public Poi getById(int id) throws PermissionException {
        LOGGER.info("getById() started, id=" + id);
        //Util.checkPermission(Constants.POI_PERM_DESCR, Util.PERM_ACTION_READ);
        Util.checkPermission(Util.isFlexRequest() ? Constants.POI_FLEX_PERM_DESCR : POI_PERM_DESCR,
                Constants.PERM_ACTION_READ);
        //Poi copy = getByIdWithoutPerm(id);
        Poi poi = (Poi) baseDao.findById(Poi.class, id);
      //  Poi copy = new Poi();
       // poi.copyTo(copy);
        LOGGER.info("getById() finished");
        return poi;
    }


    public void saveFlx(String name, int radius, double lat, double lon, int accountId, int id, String pinColor, String areaColor) throws PermissionException {
        Poi poi = new Poi();
        poi.setId(id);
        poi.setDescr(name);
        poi.setRadius(radius);
        poi.setLat(lat);
        poi.setLon(lon);
        poi.setPinColor(pinColor);
        poi.setAccount((Account) baseDao.findById(Account.class, accountId));
        poi.setAreaColor(areaColor);
        save(poi);
    }

    public void save(Poi newObj) throws PermissionException {
        LOGGER.info("save() started");
        Util.checkPermission(Util.isFlexRequest() ? Constants.POI_FLEX_PERM_DESCR : POI_PERM_DESCR,
                Constants.PERM_ACTION_WRITE);
        //Util.checkPermission(Constants.POI_PERM_DESCR, Util.PERM_ACTION_WRITE);
        Poi oldObj;
        if (newObj.getId() == 0) {
            oldObj = new Poi();
        } else {
            oldObj = (Poi) baseDao.findById(Poi.class, newObj.getId());
        }
        newObj.setDeleted(oldObj.isDeleted());
        newObj.copyTo(oldObj);
        baseDao.saveDomain(oldObj);
        LOGGER.info("save() finished");
    }


    public void markActiveInactive(int id, boolean active) throws PermissionException {
        LOGGER.info("markActiveInactive() started");
        Util.checkPermission(
                Util.isFlexRequest() ? Constants.POI_FLEX_PERM_DESCR : Constants.POI_PERM_DESCR,
                Constants.PERM_ACTION_DELETE_ARCHIVE);
        baseDao.markActiveInactive(Poi.class, id, active);
        LOGGER.info("markActiveInactive() finished");
    }

    public void deleteFlx(int id) throws ServiceException {
        delete(id);
    }

    public void delete(int id) throws ServiceException {
        LOGGER.info("delete() started");
        Util.checkPermission(
                Util.isFlexRequest() ? Constants.POI_FLEX_PERM_DESCR : Constants.POI_PERM_DESCR,
                Constants.PERM_ACTION_DELETE_ARCHIVE);
        Poi poi = (Poi) baseDao.findById(Poi.class, id);
        if (!poi.isDeleted()) {
            throw new ServiceException("POI with ID=" + poi.getId() + " cannot be deleted. Deactivate POI first.");
        }
        baseDao.removeDomain(poi);
        LOGGER.info("delete() finished");
    }

    public List<Poi> getForCurUser(Boolean active) {
        LOGGER.info("getForCurUser() started");
        List objList;
        if (SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            objList = poiDao.findAll(active);
        } else {
            int userId = SpContext.getUserDetailsInfo().getUserId();
            objList = poiDao.findForUserId(userId, active);
        }

        List<Poi> res = new ArrayList<Poi>(objList.size());
        for (Object obj : objList) {
            Poi copy = new Poi();
            ((Poi) obj).copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getForCurUser() finished");
        return res;
    }

    public List<PoiDto> getForCurUserDto(Boolean active) {
        LOGGER.info("getForCurUserDto() started");
        List<Poi> poiList = getForCurUser(active);
        List<PoiDto> resList = new ArrayList<PoiDto>(poiList.size());
        for (Poi poi : poiList) {
            resList.add(new PoiDto(poi));
        }
        LOGGER.info("getForCurUserDto() finished");
        return resList;
    }

    public List<Poi> getByAccountId(int accountId, boolean activeFilter) {
        LOGGER.info("getByAccountId() started");
        List<BaseModel> objList = baseDao.findByAccountId(Poi.class, accountId, activeFilter);
        List<Poi> result = new ArrayList<Poi>(objList.size());
        for (BaseModel model : objList) {
            Poi copy = new Poi();
            ((Poi) model).copyTo(copy);
            result.add(copy);
        }
        LOGGER.info("getByAccountId() finished");
        return result;
    }

    public List<Poi> getPoiByTimestamp(Date date) {
        List<Poi> result;

        if (date == null) {
            List<BaseModel> models = baseDao.findAll(Poi.class, true);
            result = new ArrayList<Poi>(models.size());
            for (BaseModel model : models) {
                result.add((Poi) model);
            }
        } else {
            result = poiDao.findByTimestamp(date, true);
        }
        return result;
    }
}
