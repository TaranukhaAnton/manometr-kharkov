package ua.com.manometer.service.address;

import ua.com.manometer.model.address.Area;

import java.util.List;

public interface AreaService {

	public void addArea(Area area);

	public List<Area> listArea();

	public void removeArea(Long id);

    List<Area> listAreaForCountry(Long countryId);
}
