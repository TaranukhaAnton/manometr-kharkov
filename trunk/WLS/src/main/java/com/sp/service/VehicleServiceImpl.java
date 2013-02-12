package com.sp.service;

import com.sp.SpContext;
import com.sp.dao.*;
import com.sp.dto.SearchResultDto;
import com.sp.dto.VehicleDto;
import com.sp.dto.flex.VehicleNVDto;
import com.sp.dto.mobile.VehicleMobile;
import com.sp.exception.PermissionException;
import com.sp.exception.ServiceException;
import com.sp.model.*;
import com.sp.util.Constants;
import com.sp.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.model.SelectItem;
import java.util.*;

@Service
public class VehicleServiceImpl implements VehicleService {
    private final static Logger LOGGER = Logger.getLogger(VehicleServiceImpl.class);
    
    @Autowired
    VehicleDao vehicleDao;
    
    @Autowired
    BaseDao baseDao;
    
    @Autowired
    UnitViewDao unitViewDao;

    @Autowired
    IncomingLogDao incomingLogDao;

    @Autowired
    private AccountDao accountDao;

    @Override
    public List<VehicleNVDto> getAllNetworkVehicles() {
        LOGGER.info("getAllNetworkVehicles() started");
        Integer anPostAccountId  = Integer.valueOf(Util.getNetworkVehiclesProperties().getProperty("anPostAccountId"));
        List<VehicleNVDto> allNetworkVehicles = vehicleDao.getAllNetworkVehicles(anPostAccountId);
        return allNetworkVehicles;
    }

    @Override
    public List<VehicleNVDto> getAvailableNetworkVehicles() {
        LOGGER.info("getAvailableNetworkVehicles() started");
        Integer anPostAccountId  = Integer.valueOf(Util.getNetworkVehiclesProperties().getProperty("anPostAccountId"));
        List<VehicleNVDto> vehicles = vehicleDao.getAvailableNetworkVehicles(anPostAccountId);
        return vehicles;
    }

    @Override
    public List<VehicleNVDto> getActiveNetworkVehicles() {
        LOGGER.info("getActiveNetworkVehicles() started");
        Integer anPostAccountId  = Integer.valueOf(Util.getNetworkVehiclesProperties().getProperty("anPostAccountId"));
        List<VehicleNVDto> vehicles = vehicleDao.getActiveNetworkVehicles(anPostAccountId);
        return vehicles;
    }

    @Override
    public List<VehicleNVDto> getArchivedNetworkVehicles() {
        LOGGER.info("getArchivedNetworkVehicles() started");
        Integer anPostAccountId  = Integer.valueOf(Util.getNetworkVehiclesProperties().getProperty("anPostAccountId"));
        List<VehicleNVDto> vehicles = vehicleDao.getArchivedNetworkVehicles(anPostAccountId);
        return vehicles;
    }


    public void updateNetworkVehicles(List<Integer> vehicles, boolean networkActive, boolean networkArchived) {

        LOGGER.info("updateNetworkVehicles() started, networkActive=" + networkActive + ",networkArchived" + networkArchived);
        vehicleDao.updateNetworkVehicles(vehicles, networkActive, networkArchived);
        LOGGER.info("updateNetworkVehicles() finished");
        System.out.println("updateNetworkVehicles vehicles.size() = " + vehicles.size());
    }


    @Override
    public List<Vehicle> getForCurUser(boolean activeFilter) {
        LOGGER.info("getForCurUser() started, activeFilter=" + activeFilter);
        List objList;
        if (SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            objList = baseDao.findAll(Vehicle.class, activeFilter);
        } else {
            int userId = SpContext.getUserDetailsInfo().getUserId();
            objList = vehicleDao.findForUserId(userId, activeFilter);
        }
        List<Vehicle> res = new ArrayList<Vehicle>(objList.size());
        for (Object obj : objList) {
            Vehicle copy = new Vehicle();
            ((Vehicle) obj).copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getForCurUser() finished");
        return res;
    }

    @Override
    public List<VehicleMobile> getVehiclesMobileForCurUser(String restriction) {
        LOGGER.info("getForCurUserWithRestrictions() started");
        List<VehicleMobile> objList = vehicleDao.findForUserMobile(SpContext.getUserDetailsInfo().getUserId(), true, restriction);
        LOGGER.info("getForCurUserWithRestrictions() finished");
        return objList;
    }

    @Override
    public List<Vehicle> getForUserWithRestrictions(String restriction) {
        LOGGER.info("getForCurUserWithRestrictions() started");
        List objList = vehicleDao.findForUserId(SpContext.getUserDetailsInfo().getUserId(), true, restriction);

        List<Vehicle> res = new ArrayList<Vehicle>(objList.size());
        for (Object obj : objList) {
            Vehicle copy = new Vehicle();
            ((Vehicle) obj).copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getForCurUserWithRestrictions() finished");
        return res;
    }

    @Override
    public Collection<SelectItem> getOptionsByAccountIdWithCabPhone(Integer accountId, int unitId) {
        Integer userId = null;
        if (!SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            userId = SpContext.getUserDetailsInfo().getUserId();
        }
        accountId = accountId == 0 ? null : accountId;  // accountId == 0 for IN msgs
        List<Vehicle> vehicleList = vehicleDao.findByAccountIdWithCabPhone(accountId, unitId, userId);
        List<Vehicle> res = new ArrayList<Vehicle>(vehicleList.size());
        for (Vehicle veh : vehicleList) {
            Vehicle dto = new Vehicle();
            veh.copyTo(dto);
            res.add(dto);
        }
        return Util.modelsToSelectItems(res);
    }

    @Override
    public Vehicle getByCabPhone(String cabPhone) {
        Vehicle v = vehicleDao.findByCabPhone(cabPhone);
        if (v == null) {
            return null;
        }
        Vehicle dto = new Vehicle();
        v.copyTo(dto);
        return dto;
    }

    @Override
    public Vehicle getById(int id) throws PermissionException {
        return getById(id, true);
    }

    @Override
    public Vehicle getById(int id, boolean securityCheckNeeded) throws PermissionException {
        LOGGER.info("getById() started, id=" + id);
        if (securityCheckNeeded) {
            SpContext.getUserDetailsInfo().checkAuthority(UNIT_PERM_DESCR, Util.PERM_ACTION_READ);
        }

        Vehicle obj = (Vehicle) baseDao.findById(Vehicle.class, id);
        Vehicle dto = new Vehicle();
        obj.copyTo(dto);
        LOGGER.info("getById() finished");
        return dto;
    }

    @Override
    public List<SearchResultDto> getByRegistrationNumberExample(String registrationNumberExample) {

        String restrictedVehicleIdsCommaSeparated = SpContext.getUserDetailsInfo().getRestrictedVehicleIdsCommaSeparated();
        List restrictedVehicleIds = Arrays.asList(restrictedVehicleIdsCommaSeparated.split(","));
        registrationNumberExample = registrationNumberExample.replaceAll("[^A-Za-z0-9-_]", "");
        if (registrationNumberExample.isEmpty()) {
            return new ArrayList<SearchResultDto>(0);
        }
        Integer userId = null;
        if (!SpContext.getUserDetailsInfo().getAuthoritiesMap().containsKey(Constants.ROLE_ADMIN)) {
            userId = SpContext.getUserDetailsInfo().getUserId();
        }
        List<SearchResultDto> result = vehicleDao.findBySearchStr("%" + registrationNumberExample + "%", restrictedVehicleIds, userId);
        return result;
    }

    public VehicleDto getVehicleDtoById(Vehicle vehicle) {
        VehicleDto vehicleDto;

        BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(vehicle.getId());
        vehicleDto = new VehicleDto(vehicle, trackingDevice, false, "");
        return vehicleDto;
    }


    public VehicleDto getVehicleDtoById(int vehicleId) {
        VehicleDto vehicleDto;
        Vehicle vehicle = (Vehicle) vehicleDao.findById(Vehicle.class, vehicleId);
        BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(vehicle.getId());
        vehicleDto = new VehicleDto(vehicle, trackingDevice, false, "");
        return vehicleDto;
    }

    @Override
    public BaseVehicle getBaseVehicleById(int id) throws PermissionException {
        LOGGER.info("getById() started, id=" + id);
        SpContext.getUserDetailsInfo().checkAuthority(UNIT_PERM_DESCR, Util.PERM_ACTION_READ);

        BaseVehicle obj = (BaseVehicle) baseDao.findById(BaseVehicle.class, id);
        BaseVehicle dto = new BaseVehicle();
        obj.copyTo(dto);
        LOGGER.info("getById() finished");
        return dto;
    }

    @Override
    public void save(Vehicle vehicle) throws ServiceException {
        save(vehicle, null, null, true);
    }

    @Override
    public void saveImmobiliseStatus(VehicleDto vehicleDto) throws ServiceException {
        Vehicle vehicle = (Vehicle) baseDao.findById(Vehicle.class, vehicleDto.getId());
        vehicle.setImmobilizeStatus(vehicleDto.isImmobilizeStatus());
        if (vehicleDto.getImmobilizationDate() != null) {
            vehicle.setImmobilizationDate(new Date(vehicleDto.getImmobilizationDate()));
        }
        save(vehicle, null, null, true);
    }

    @Override
    public void saveClientInfo(VehicleDto vehicle) throws ServiceException {
        SpContext.getUserDetailsInfo().checkAuthority(Constants.INFORMATION_PERM_DESCR, Constants.PERM_ACTION_MANAGE);
        Vehicle vehicleToSave = (Vehicle) baseDao.findById(Vehicle.class, vehicle.getId());
        vehicleToSave.setClientInfo(vehicle.getClientInfo());
        save(vehicleToSave, null, null, false);
    }

    @Override
    public void saveLightVehicle(LightVehicle vehicle) {
        LightVehicle oldObj = (LightVehicle) baseDao.findById(LightVehicle.class, vehicle.getId());
        vehicle.copyTo(oldObj);
        baseDao.saveDomain(oldObj);
    }

    @Override
    public void save(Vehicle newObj,
                     List<Integer> groupListIds,
                     List<String> selectedAccountValues,
                     boolean withPermissions) throws ServiceException {
        LOGGER.info("save() started, newObj.id=" + newObj.getId());
        if (withPermissions) {
            SpContext.getUserDetailsInfo().checkAuthority(UNIT_PERM_DESCR, Util.PERM_ACTION_WRITE);
        }

        newObj.setRegistrationNumber(newObj.getRegistrationNumber().trim());
        Vehicle existingVehicle = vehicleDao.findByRegistrationNumber(newObj.getRegistrationNumber());
        if (existingVehicle != null && existingVehicle.getId() != newObj.getId()) {
            List<MapUnitAccount> mapUnitAccounts = vehicleDao.findMapVehicleAccountsByVehicleId(existingVehicle.getId());
            if (mapUnitAccounts != null && mapUnitAccounts.size() > 0) {
                Account account = (Account) baseDao.findById(Account.class, mapUnitAccounts.get(0).getAccountId());
                throw new ServiceException("Vehicle with RegistrationNumber=[" + existingVehicle.getRegistrationNumber()
                        + "] (ID=" + existingVehicle.getId()
                        + ") already exists under account=" + account.getDescr() + ". Please enter a unique Vehicle Registration Number.");
            } else {
                throw new ServiceException("Vehicle with RegistrationNumber=[" + existingVehicle.getRegistrationNumber()
                        + "] (ID=" + existingVehicle.getId()
                        + ") already exists. Please enter a unique Vehicle Registration Number.");
            }
        }

        AuditEvent event = new AuditEvent();
        Vehicle oldObj;
        boolean isNew = false;
        if (newObj.getId() == 0) {
            newObj.setRenewalDate(new Date());
            oldObj = newObj;
            isNew = true;
        } else {
            oldObj = (Vehicle) baseDao.findById(Vehicle.class, newObj.getId());
            newObj.copyTo(oldObj);
            oldObj.setType(getVehicleTypeById(newObj.getType().getId()));
        }
        baseDao.saveDomain(oldObj);
        if (groupListIds != null) {
            List<Integer> vehicleViewIdList = new ArrayList<Integer>(groupListIds.size());
            for (Integer groupId : groupListIds) {
                vehicleViewIdList.add(groupId);
            }
            saveVehicleToVehicleViews(oldObj, vehicleViewIdList);
        }
        if (selectedAccountValues != null) {
            List<MapUnitAccount> list = new ArrayList<MapUnitAccount>();
            for (String accountId : selectedAccountValues) {
                MapUnitAccount mua = new MapUnitAccount();
                mua.setAccountId(Integer.parseInt(accountId));
                mua.setVrehicleId(oldObj.getId());
                list.add(mua);
            }
            saveMapVehicleAccountList(oldObj.getId(), list);
        }
        if (withPermissions) {
            event.setEventType(isNew ? "Addition" : "Updating");
            baseDao.initAuditEvent(event);
            event.setCategory(UNIT_PERM_DESCR);
            event.setDescr(event.getEventType() + " of " + event.getCategory() + " " + oldObj.getRegistrationNumber() + "(" + oldObj.getId() + ")");
            baseDao.saveDomain(event);
        }
        LOGGER.info("save() finished");
    }


    private void saveMapVehicleAccountList(int vehicleId, List<MapUnitAccount> mapUnitAccountList) {

        List<MapUnitAccount> oldMapVehicleAccountList = vehicleDao.findMapVehicleAccountsByVehicleId(vehicleId);
        for (MapUnitAccount mapUnitAccount : oldMapVehicleAccountList) {
            if (!isMapUnitAccountInCollection(mapUnitAccount, mapUnitAccountList)) {
                baseDao.removeDomain(mapUnitAccount);
            }
        }

        for (MapUnitAccount mua : mapUnitAccountList) {
            if (!isMapUnitAccountInCollection(mua, oldMapVehicleAccountList)) {
                saveMapUnitAccount(mua);
            }
        }
    }

    private boolean isMapUnitAccountInCollection(MapUnitAccount mapUnitAccount, Collection<MapUnitAccount> mapUnitAccountList) {
        for (MapUnitAccount mua : mapUnitAccountList) {
            if (mua.getAccountId() == mapUnitAccount.getAccountId()
                    && mua.getVrehicleId() == mapUnitAccount.getVrehicleId()) {
                return true;
            }
        }
        return false;
    }

    private void saveMapUnitAccount(MapUnitAccount newObj) {
        MapUnitAccount oldObj;
        if (newObj.getId() == 0) {
            oldObj = newObj;
        } else {
            oldObj = (MapUnitAccount) baseDao.findById(MapUnitAccount.class, newObj.getId());
            newObj.copyTo(oldObj);
        }

        baseDao.saveDomain(oldObj);
    }

    @Override
    public void saveVehicleToVehicleViews(Vehicle vehicle, List<Integer> vehicleViewIdList) {
        LOGGER.info("saveVehicleToVehicleViews() started");
        List<UnitView> viewList = unitViewDao.findByVehicleId(vehicle.getId());
        for (UnitView unitView : viewList) {
            unitViewDao.removeByVehicleAndGroupIds(vehicle.getId(), unitView.getId());
        }
        List newVehiclesByList = baseDao.findBaseItemViewsByListIds(UnitView.class, vehicleViewIdList);
        for (Object view : newVehiclesByList) {
            UnitView uv = (UnitView) view;
            unitViewDao.saveMapVehicleVeiw(vehicle.getId(), uv.getId());
        }
        LOGGER.info("saveVehicleToVehicleViews() finished");
    }

    @Override
    public List<Vehicle> getAllList(Boolean active) //throws PermissionException
    {
        LOGGER.info("getAllList() started, active=" + active);
        //Util.checkPermission(UNIT_PERM_DESCR, Util.PERM_ACTION_READ);

        List<BaseModel> objList = baseDao.findAll(Vehicle.class, active);
        List<Vehicle> res = new ArrayList<Vehicle>(objList.size());
        for (BaseModel obj : objList) {
            Vehicle dto = new Vehicle();
            ((Vehicle) obj).copyTo(dto);
            res.add(dto);
        }
        LOGGER.info("getAllList() finished");
        return res;
    }

    @Override
    public List<EnumModel> getOptionsByUnitViewId(int unitViewId) {
        LOGGER.info("getOptionsByUnitViewId() started");
        List<VehicleDto> objSet = getNonExpiredByUnitViewIdRefreshed(unitViewId);
        List<EnumModel> res = new ArrayList<EnumModel>(objSet.size());
        for (VehicleDto vehicle : objSet) {
            EnumModel dto = new EnumModel();
            dto.setId(vehicle.getId());
            dto.setDescr(vehicle.getRegistrationNumber());
            res.add(dto);
        }
        LOGGER.info("getOptionsByUnitViewId() finished");
        return res;
    }

    @Override
    public List<Vehicle> getByUnitViewId(int unitViewId, boolean active) {
        UnitView unitView = (UnitView) baseDao.findById(UnitView.class, unitViewId);
        Set<Vehicle> objSet = unitView.getGroupVehicles();
        List<Vehicle> res = new ArrayList<Vehicle>(objSet.size());
        for (Vehicle vehicle : objSet) {
            if (vehicle.isDeleted() != active) {
                Vehicle dto = new Vehicle();
                vehicle.copyTo(dto);
                res.add(dto);
            }
        }
        return res;
    }

    @Override
    public List<Vehicle> getByUnitViewIdAndDeviceTypeId(int unitViewId, Boolean active, int deviceTypeId) {
        LOGGER.info("getByUnitViewIdAndDeviceTypeId() started, unitViewId=" + unitViewId +
                ",active=" + active + "deviceTypeId=" + deviceTypeId);
        List<Vehicle> objList = vehicleDao.
                findByUnitViewIdAndDeviceTypeId(unitViewId, active, deviceTypeId, SpContext.getUserDetailsInfo().getRestrictedVehicleIdsCommaSeparated());
        List<Vehicle> res = new ArrayList<Vehicle>(objList.size());
        for (Vehicle obj : objList) {
            Vehicle dto = new Vehicle();
            obj.copyTo(dto);
            res.add(dto);
        }
        LOGGER.info("getByUnitViewIdAndDeviceTypeId finished");
        return res;
    }

    @Override
    public List<VehicleType> getVehicleTypes() {
        LOGGER.info("getVehicleTypes() started");
        List<BaseModel> objList = baseDao.findAll(VehicleType.class);
        List<VehicleType> res = new ArrayList<VehicleType>(objList.size());
        for (Object obj : objList) {
            res.add((VehicleType) obj);
        }
        LOGGER.info("getVehicleTypes() finished");
        return res;
    }

    @Override
    public VehicleType getVehicleTypeById(int id) {
        return (VehicleType) baseDao.findById(VehicleType.class, id);
    }

    @Override
    public void saveVehicleType(VehicleType newObj) {
        LOGGER.info("saveVehicleType() started, newObj.id=" + newObj.getId());
        VehicleType oldObj;
        if (newObj.getId() == 0) {
            oldObj = newObj;
        } else {
            oldObj = (VehicleType) baseDao.findById(VehicleType.class, newObj.getId());
            newObj.copyTo(oldObj);
        }

        baseDao.saveDomain(oldObj);
        LOGGER.info("saveVehicleType() finished");
    }

    @Override
    public List<BoxType> getBoxTypes() {
        LOGGER.info("getBoxTypes() started");
        List<BaseModel> objList = baseDao.findAll(BoxType.class);
        List<BoxType> res = new ArrayList<BoxType>(objList.size());
        for (Object obj : objList) {
            res.add((BoxType) obj);
        }
        LOGGER.info("getBoxTypes() finished");
        return res;
    }

    @Override
    public BoxType getBoxTypeById(int id) {
        return (BoxType) baseDao.findById(BoxType.class, id);
    }

    @Override
    public Set<BoxType> getBoxTypesByIdSet(Set<Integer> ids) {
        LOGGER.info("getBoxTypesByIdSet() started");
        List<BaseModel> objList = baseDao.findByIdCollection(BoxType.class, ids);
        Set<BoxType> res = new HashSet<BoxType>(objList.size());
        for (BaseModel obj : objList) {
            res.add((BoxType) obj);
        }
        LOGGER.info("getBoxTypesByIdSet() finished");
        return res;
    }

    @Override
    public List<VehicleStatus> getVehicleStatusList() {
        LOGGER.info("getVehicleStatusList() started");
        List<BaseModel> objList = baseDao.findAll(VehicleStatus.class);
        List<VehicleStatus> res = new ArrayList<VehicleStatus>(objList.size());
        for (BaseModel obj : objList) {
            res.add((VehicleStatus) obj);
        }
        LOGGER.info("getVehicleStatusList() finished");
        return res;
    }

    @Override
    public VehicleStatus getStatusById(int id) {
        return (VehicleStatus) baseDao.findById(VehicleStatus.class, id);
    }

    @Override
    public void markActiveInactive(int id, boolean active) throws PermissionException {
        LOGGER.info("markActiveInactive() started, id=" + id + ",active" + active);
        Util.checkPermission(UNIT_PERM_DESCR, Util.PERM_ACTION_DELETE_ARCHIVE);
        vehicleDao.markActiveInactive(id, active);
        LOGGER.info("markActiveInactive() finished");
    }

    @Override
    public List<MapUnitAccount> getMapUnitAccountsByVehicleId(int vehicleId) {
        LOGGER.info("getMapUnitAccountsByVehicleId() started, vehicleId=" + vehicleId);
        List<MapUnitAccount> objList = vehicleDao.findMapVehicleAccountsByVehicleId(vehicleId);
        List<MapUnitAccount> res = new ArrayList<MapUnitAccount>(objList.size());
        for (MapUnitAccount obj : objList) {
            MapUnitAccount copy = new MapUnitAccount();
            obj.copyTo(copy);
            res.add(copy);
        }
        LOGGER.info("getMapUnitAccountsByVehicleId() finished");
        return res;
    }

    @Override
    public List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId) {
        return getNonExpiredByUnitViewIdRefreshed(unitViewId, null, null);
    }

    @Override
    public List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId, Date refreshDate) {
        return getNonExpiredByUnitViewIdRefreshed(unitViewId, refreshDate, null);
    }

    @Override
    public List<VehicleDto> getNonExpiredVehicleListForBackToTheFuture(Date pastDate, List<VehicleDto> nowadaysVehicleList) {
        LOGGER.info("getNonExpiredVehicleListForBackToTheFuture() started");
        List<Integer> vehicleIdList = Util.modelsToIds(nowadaysVehicleList);
        List<IncomingLogRecord> incomingLogRecordList =incomingLogDao.findBackToTheFutureLogList(pastDate, vehicleIdList);
        List<VehicleDto> res = new ArrayList<VehicleDto>(incomingLogRecordList.size());
        for (IncomingLogRecord logRecord : incomingLogRecordList) {
            for (VehicleDto nowadaysVehicle : nowadaysVehicleList) {
                if (nowadaysVehicle.getId() == logRecord.getVehicleId()) {
                    BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(logRecord.getVehicleId());
                    // create fake vehicle with Back To The Future Din values for decodeDinValues method
                    Vehicle vehicleWithPastDinInfo = new Vehicle();
                    vehicleWithPastDinInfo.setDigitalInput1High(logRecord.isInput1High());
                    vehicleWithPastDinInfo.setDigitalInput2High(logRecord.isInput2High());
                    vehicleWithPastDinInfo.setDigitalInput3High(logRecord.isInput3High());
                    vehicleWithPastDinInfo.setDigitalInput4High(logRecord.isInput4High());
                    vehicleWithPastDinInfo.setDigitalInput5High(logRecord.isInput5High());
                    vehicleWithPastDinInfo.setDigitalInput6High(logRecord.isInput6High());
                    decodeDinValues(vehicleWithPastDinInfo, trackingDevice);
                    VehicleDto backToTheFutureVehicleDto = new VehicleDto(logRecord, nowadaysVehicle, vehicleWithPastDinInfo, trackingDevice);
                    res.add(backToTheFutureVehicleDto);
                }

            }
        }
        LOGGER.info("getNonExpiredVehicleListForBackToTheFuture() finished");
        return res;
    }

    @Override
    public List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId, Date refreshDate, String repType) {
        LOGGER.info("getNonExpiredByUnitViewIdRefreshed() started, unitViewId=" + unitViewId + ",refreshDate=" + refreshDate);

        List<Vehicle> objList = vehicleDao.findNonExpiredByUnitViewIdRefreshed(unitViewId,
                refreshDate,
                repType,
                SpContext.getUserDetailsInfo().getRestrictedVehicleIdsCommaSeparated());
        Account account = accountDao.findByUnitViewId(unitViewId);
        boolean satelliteDrift = false;
        if (account != null) {
            satelliteDrift = account.isSatelliteDriftOverwrite();
        }
        List<VehicleDto> res = new ArrayList<VehicleDto>();
        for (Vehicle v : objList) {
            String address = null;
            BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(v.getId());
            if (trackingDevice != null) {
                decodeDinValues(v, trackingDevice);
            }
            if (!v.isIgnitionActive() && satelliteDrift && v.getLastActiveMsgId() > 0) {

                IncomingLogRecord logRecord = (IncomingLogRecord)incomingLogDao.findById(IncomingLogRecord.class, v.getLastActiveMsgId());
                if (logRecord != null) {
                    address = Util.getJoinedStrs(logRecord.getStreetName(), logRecord.getPostcode());
                }
            }
            res.add(new VehicleDto(v, trackingDevice, repType, satelliteDrift, address));
        }
        LOGGER.info("getNonExpiredByUnitViewIdRefreshed() finished, res.size()=" + res.size());
        return res;
    }

    @Override
    public void saveStealthMode(int unitId, boolean stealthMode) {
        Vehicle oldObj = (Vehicle) baseDao.findById(Vehicle.class, unitId);
        if (oldObj.isInStealthMode() != stealthMode) {
            oldObj.setInStealthMode(stealthMode);
            baseDao.saveOrUpdateDomain(oldObj);
        }
    }

    private void decodeDinValues(Vehicle vehicle, BaseTrackingDevice trackingDevice) {
        LOGGER.info("decodeDinValues() started");
        int dinCount = 0;
        int lastDinNumber = 0;

        if (Constants.FM2_BOX_TYPE_ID_LIST.contains(trackingDevice.getBoxType().getId()) &&
                SpContext.getUserDetailsInfo().isLeftPanelDisplayDinAllowedFm2x()) {
            if (trackingDevice.getIgnitionInputNumber() != 1 && trackingDevice.getStealthModeInputNumber() != 1
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput1Name())) {
                dinCount++;
                if (vehicle.isDigitalInput1High()) {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueHigh());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1HighColor());
                } else {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueLow());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1LowColor());
                }
                lastDinNumber = 1;
            }
            if (trackingDevice.getIgnitionInputNumber() != 2 && trackingDevice.getStealthModeInputNumber() != 2
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput2Name())) {
                dinCount++;
                if (vehicle.isDigitalInput2High()) {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueHigh());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2HighColor());
                } else {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueLow());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2LowColor());
                }
                lastDinNumber = 2;
            }
            vehicle.setActiveDinsCount(dinCount);
            vehicle.setLastDinNumber(lastDinNumber);
        } else if (Constants.FM4_BOX_TYPE_ID_LIST.contains(trackingDevice.getBoxType().getId()) &&
                SpContext.getUserDetailsInfo().isLeftPanelDisplayDinAllowedFm4x()) {
            if (trackingDevice.getIgnitionInputNumber() != 1 && trackingDevice.getStealthModeInputNumber() != 1
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput1Name())) {
                dinCount++;
                if (vehicle.isDigitalInput1High()) {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueHigh());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1HighColor());
                } else {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueLow());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1LowColor());
                }
                lastDinNumber = 1;
            }
            if (trackingDevice.getIgnitionInputNumber() != 2 && trackingDevice.getStealthModeInputNumber() != 2
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput2Name())) {
                dinCount++;
                if (vehicle.isDigitalInput2High()) {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueHigh());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2HighColor());
                } else {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueLow());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2LowColor());
                }
                lastDinNumber = 2;
            }
            if (trackingDevice.getIgnitionInputNumber() != 3 && trackingDevice.getStealthModeInputNumber() != 3
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput3Name())) {
                dinCount++;
                if (vehicle.isDigitalInput3High()) {
                    vehicle.setDigitalInput3Value(trackingDevice.getDigitalInput3ValueHigh());
                    vehicle.setDigitalInput3Color(trackingDevice.getDigitalInput3HighColor());
                } else {
                    vehicle.setDigitalInput3Value(trackingDevice.getDigitalInput3ValueLow());
                    vehicle.setDigitalInput3Color(trackingDevice.getDigitalInput3LowColor());
                }
                lastDinNumber = 3;
            }
            if (trackingDevice.getIgnitionInputNumber() != 4 && trackingDevice.getStealthModeInputNumber() != 4
                    && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput4Name())) {
                dinCount++;
                if (vehicle.isDigitalInput4High()) {
                    vehicle.setDigitalInput4Value(trackingDevice.getDigitalInput4ValueHigh());
                    vehicle.setDigitalInput4Color(trackingDevice.getDigitalInput4HighColor());
                } else {
                    vehicle.setDigitalInput4Value(trackingDevice.getDigitalInput4ValueLow());
                    vehicle.setDigitalInput4Color(trackingDevice.getDigitalInput4LowColor());
                }
                lastDinNumber = 4;
            }
            vehicle.setActiveDinsCount(dinCount);
            vehicle.setLastDinNumber(lastDinNumber);
        } else if (trackingDevice.getBoxType().getId() == Constants.FM11_BOX_TYPE_ID
                && SpContext.getUserDetailsInfo().isLeftPanelDisplayDinAllowedFm11()) {
            if (trackingDevice.getStealthModeInputNumber() != 2 && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput2Name())) {
                dinCount++;
                if (vehicle.isDigitalInput2High()) {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueHigh());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2HighColor());
                } else {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueLow());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2LowColor());
                }
                lastDinNumber = 2;
            }
            if (trackingDevice.getStealthModeInputNumber() != 3 && !Util.isNullOrEmptyStr(trackingDevice.getDigitalInput3Name())) {
                dinCount++;
                if (vehicle.isDigitalInput3High()) {
                    vehicle.setDigitalInput3Value(trackingDevice.getDigitalInput3ValueHigh());
                    vehicle.setDigitalInput3Color(trackingDevice.getDigitalInput3HighColor());
                } else {
                    vehicle.setDigitalInput3Value(trackingDevice.getDigitalInput3ValueLow());
                    vehicle.setDigitalInput3Color(trackingDevice.getDigitalInput3LowColor());
                }
                lastDinNumber = 3;
            }
            vehicle.setActiveDinsCount(dinCount);
            vehicle.setLastDinNumber(lastDinNumber);
        } else if ((trackingDevice.getBoxType().getId() == Constants.T6_PREMIUM_BOX_TYPE_ID || trackingDevice.getBoxType().getId() == Constants.T8_PREMIUM_BOX_TYPE_ID)
                && SpContext.getUserDetailsInfo().isLeftPanelDisplayDinAllowedT6()) {
            if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput1Name())) {
                dinCount++;
                if (vehicle.isDigitalInput1High()) {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueHigh());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1HighColor());
                } else {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueLow());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1LowColor());
                }
                lastDinNumber = 1;
            }
            if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput2Name())) {
                dinCount++;
                if (vehicle.isDigitalInput2High()) {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueHigh());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2HighColor());
                } else {
                    vehicle.setDigitalInput2Value(trackingDevice.getDigitalInput2ValueLow());
                    vehicle.setDigitalInput2Color(trackingDevice.getDigitalInput2LowColor());
                }
                lastDinNumber = 2;
            }
            vehicle.setActiveDinsCount(dinCount);
            vehicle.setLastDinNumber(lastDinNumber);
        } else if ((trackingDevice.getBoxType().getId() == Constants.T6_LITE_BOX_TYPE_ID || trackingDevice.getBoxType().getId() == Constants.T8_LITE_BOX_TYPE_ID)
                && SpContext.getUserDetailsInfo().isLeftPanelDisplayDinAllowedT6()) {
            if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput1Name())) {
                dinCount++;
                if (vehicle.isDigitalInput1High()) {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueHigh());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1HighColor());
                } else {
                    vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueLow());
                    vehicle.setDigitalInput1Color(trackingDevice.getDigitalInput1LowColor());
                }
                lastDinNumber = 1;
            }
            vehicle.setActiveDinsCount(dinCount);
            vehicle.setLastDinNumber(lastDinNumber);
        } else {
            switch (trackingDevice.getLeftPanelDisplayDinNumber()) {
                case 1:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput1Name())) {
                        if (vehicle.isDigitalInput1High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput1ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
                case 2:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput2Name())) {
                        if (vehicle.isDigitalInput2High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput2ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput2ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
                case 3:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput3Name())) {
                        if (vehicle.isDigitalInput3High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput3ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput3ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
                case 4:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput4Name())) {
                        if (vehicle.isDigitalInput4High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput4ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput4ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
                case 5:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput5Name())) {
                        if (vehicle.isDigitalInput5High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput5ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput5ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
                case 6:
                    if (!Util.isNullOrEmptyStr(trackingDevice.getDigitalInput6Name())) {
                        if (vehicle.isDigitalInput6High()) {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput6ValueHigh());
                        } else {
                            vehicle.setDigitalInput1Value(trackingDevice.getDigitalInput6ValueLow());
                        }
                    }
                    vehicle.setActiveDinsCount(1);
                    vehicle.setLastDinNumber(1);
                    break;
            }
        }

        LOGGER.info("decodeDinValues() finished");
    }

    private List<BaseVehicle> getByTimestamp(Date date) {
        List<BaseVehicle> vehicleList;

        if (date == null) {
            LOGGER.info("getByTimestamp(): reading all vehicles");
            List<BaseModel> models = baseDao.findAll(BaseVehicle.class);
            vehicleList = new ArrayList<BaseVehicle>(models.size());
            for (BaseModel model : models) {
                vehicleList.add((BaseVehicle) model);
            }
        } else {
            LOGGER.info("getByTimestamp(): reading vehicles by Timestamp=" + date);
            vehicleList = vehicleDao.findByTimestamp(date);
        }
        return vehicleList;
    }

    @Override
    public Map<Integer, BaseVehicle> getVehicleMap(Date date) {
        List<BaseVehicle> trackingDevices = getByTimestamp(date);
        Map<Integer, BaseVehicle> result = new HashMap<Integer, BaseVehicle>();
        for (BaseVehicle trackingDevice : trackingDevices) {
            BaseVehicle dto = new BaseVehicle();
            trackingDevice.copyTo(dto);
            result.put(trackingDevice.getId(), dto);
        }
        return result;
    }

    @Override
    public List<LightVehicle> getVehiclesForImmobilisation() {
        List<LightVehicle> models = vehicleDao.findWithImmobilisationDate();
        List<LightVehicle> result = new ArrayList<LightVehicle>(models.size());
        for (LightVehicle vehicle : models) {
            LightVehicle dto = new LightVehicle();
            vehicle.copyTo(dto);
            result.add(dto);
        }
        return result;
    }
}


