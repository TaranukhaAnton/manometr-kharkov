package com.sp.service;

import com.sp.exception.ServiceException;
import com.sp.model.AccountedMovableItemView;
import com.sp.model.BaseMovableItemView;
import com.sp.model.UnitView;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Oleg
 * Date: 26.09.12
 * Time: 18:14
 * To change this template use File | Settings | File Templates.
 */
public interface UnitViewService {
    List<UnitView> getByParentId(int parentId);

    List<BaseMovableItemView> getBaseItemtRootsByAccountId(int accountId);

    List<UnitView> getByVehicleId(int vehicleId);

    Integer getLastUnitViewByUnitId(int userId);

    void save(UnitView newObj);

    void deleteAllEmptyUnassignedGroups();

    AccountedMovableItemView getBaseItemById(Integer id);

    AccountedMovableItemView getBaseItemByIdWithoutDisabledVehicles(Integer id);

    UnitView getById(int id);

    void deleteGroupCreateUnassigned(int deleteId);

    void delete(int id) throws ServiceException;

    List<UnitView> getAllByUserIdAndAccount(List<Integer> accounts);

    List<UnitView> getAllByUserId();

    List<UnitView> getAllByUserIdWithRestrictions(String restricted);
}
