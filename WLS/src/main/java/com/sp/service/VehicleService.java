package com.sp.service;

import com.sp.dto.SearchResultDto;
import com.sp.dto.VehicleDto;
import com.sp.dto.flex.VehicleNVDto;
import com.sp.dto.mobile.VehicleMobile;
import com.sp.exception.PermissionException;
import com.sp.exception.ServiceException;
import com.sp.model.*;

import javax.faces.model.SelectItem;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 06.09.12
 * Time: 12:46
 * To change this template use File | Settings | File Templates.
 */
public interface VehicleService {
    String UNIT_PERM_DESCR = "Unit";
    String UNIT_DETAILED_PERM_DESCR = "Unit-Detailed";
    String UNIT_GROUPS_PERM_DESCR = "Unit-Groups";
    String UNIT_ACCOUNTS_PERM_DESCR = "Unit-Accounts";

    List<Vehicle> getForCurUser(boolean activeFilter);

    List<VehicleNVDto> getAllNetworkVehicles();

    List<VehicleNVDto> getAvailableNetworkVehicles();

    List<VehicleNVDto> getActiveNetworkVehicles();

    List<VehicleNVDto> getArchivedNetworkVehicles();

    void updateNetworkVehicles(List<Integer> vehicles, boolean networkActive, boolean networkArchived);

    List<Vehicle> getForUserWithRestrictions(String restriction);

    Collection<SelectItem> getOptionsByAccountIdWithCabPhone(Integer accountId, int unitId);

    Vehicle getByCabPhone(String cabPhone);

    Vehicle getById(int id) throws PermissionException;

    Vehicle getById(int id, boolean securityCheckNeeded) throws PermissionException;

    BaseVehicle getBaseVehicleById(int id) throws PermissionException;

    List<SearchResultDto> getByRegistrationNumberExample(String registrationNumberExample);


    VehicleDto getVehicleDtoById(int vehicleId);

    void save(Vehicle vehicle) throws ServiceException;

    void saveImmobiliseStatus(VehicleDto vehicleDto) throws ServiceException;

    void saveClientInfo(VehicleDto vehicle) throws ServiceException;

    void saveLightVehicle(LightVehicle vehicle);

    void save(Vehicle newObj,
              List<Integer> groupListIds,
              List<String> selectedAccountValues,
              boolean withPermissions) throws ServiceException;

    void saveVehicleToVehicleViews(Vehicle vehicle, List<Integer> vehicleViewIdList);

    List<Vehicle> getAllList(Boolean active); //throws PermissionException

    List<EnumModel> getOptionsByUnitViewId(int unitViewId);

    List<Vehicle> getByUnitViewId(int unitViewId, boolean active);

    List<Vehicle> getByUnitViewIdAndDeviceTypeId(int unitViewId, Boolean active, int deviceTypeId);

    List<VehicleType> getVehicleTypes();

    VehicleType getVehicleTypeById(int id);

    void saveVehicleType(VehicleType newObj);

    List<BoxType> getBoxTypes();

    BoxType getBoxTypeById(int id);

    Set<BoxType> getBoxTypesByIdSet(Set<Integer> ids);

    List<VehicleStatus> getVehicleStatusList();

    VehicleStatus getStatusById(int id);

    void markActiveInactive(int id, boolean active) throws PermissionException;

    List<MapUnitAccount> getMapUnitAccountsByVehicleId(int vehicleId);

    List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId);

    List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId, Date refreshDate);

    List<VehicleDto> getNonExpiredVehicleListForBackToTheFuture(Date pastDate, List<VehicleDto> nowadaysVehicleList);

    List<VehicleDto> getNonExpiredByUnitViewIdRefreshed(int unitViewId, Date refreshDate, String repType);

    void saveStealthMode(int unitId, boolean stealthMode);

    Map<Integer, BaseVehicle> getVehicleMap(Date date);

    List<LightVehicle> getVehiclesForImmobilisation();

    List<VehicleMobile> getVehiclesMobileForCurUser(String restrictions);
}
