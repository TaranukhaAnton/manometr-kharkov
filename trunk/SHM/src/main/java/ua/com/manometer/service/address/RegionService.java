package ua.com.manometer.service.address;

import ua.com.manometer.model.address.Region;

import java.util.List;

public interface RegionService {

	public void addRegion(Region region);

	public List<Region> listRegion();

	public void removeRegion(Long id);

}
