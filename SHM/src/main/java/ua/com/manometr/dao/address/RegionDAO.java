package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.Region;

import java.util.List;

public interface RegionDAO {

	public void addRegion(Region region);

	public List<Region> listRegion();

	public void removeRegion(Long id);

}
