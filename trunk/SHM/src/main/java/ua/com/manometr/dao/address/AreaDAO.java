package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.Area;
import ua.com.manometr.model.address.Country;

import java.util.List;

public interface AreaDAO {

	public void addArea(Area area);

	public List<Area> listArea();

	public void removeArea(Long id);

    public List<Area> listAreaForCountry(Long country);
}
