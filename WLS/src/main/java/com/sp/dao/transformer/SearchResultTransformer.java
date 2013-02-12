package com.sp.dao.transformer;

import com.sp.dto.SearchResultDto;
import com.sp.model.Vehicle;
import org.apache.commons.lang.StringUtils;
import org.hibernate.transform.ResultTransformer;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: windows7
 * Date: 19.11.12
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class SearchResultTransformer implements ResultTransformer {

    @Override
    public Object transformTuple(Object[] objects, String[] strings) {
        Vehicle vehicle = (Vehicle) objects[0];
        Integer groupId = (Integer) objects[1];
        String groupName = StringUtils.isBlank((String) objects[2])?"-":(String) objects[2];
        String information = StringUtils.isBlank((String) objects[3])?"-":(String) objects[3];
        String accountName = StringUtils.isBlank((String) objects[4])?"-":(String) objects[4];
        vehicle.setVin(StringUtils.isBlank(vehicle.getVin())?"-":vehicle.getVin());
        Integer accountId = (Integer) objects[5];
        SearchResultDto dto = new SearchResultDto(accountId, accountName, groupId, groupName, information, vehicle);
        return dto;
    }

    @Override
    public List transformList(List list) {
        return list;
    }


}
