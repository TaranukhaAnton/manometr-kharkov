package ua.com.manometer.service.address;

import ua.com.manometer.model.address.Area;

import java.util.List;

public interface AreaService {

	public void addArea(Area area);

    public  List<Area> listAreaForCountry(Integer countryId);
}
