package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.Area;

import java.util.List;

public interface AreaDAO {

	public void addArea(Area area);


    public List<Area> listAreaForCountry(Long country);
}
