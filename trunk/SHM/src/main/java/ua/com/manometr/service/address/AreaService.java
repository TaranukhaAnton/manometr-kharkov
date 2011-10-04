package ua.com.manometr.service.address;

import ua.com.manometr.model.address.Area;

import java.util.List;

public interface AreaService {

	public void addArea(Area area);

	public List<Area> listArea();

	public void removeArea(Long id);

}
