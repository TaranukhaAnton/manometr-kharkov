package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.Region;

import java.util.List;

public interface RegionDAO {

	public void addRegion(Region region);

	public List<Region> listRegion();



}
