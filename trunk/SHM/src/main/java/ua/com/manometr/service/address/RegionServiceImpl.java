package ua.com.manometr.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.address.RegionDAO;
import ua.com.manometr.model.address.Region;

import java.util.List;

@Service
public class RegionServiceImpl implements RegionService {

	@Autowired
	private RegionDAO regionDAO;

	@Override
	@Transactional
	public void addRegion(Region region) {
		regionDAO.addRegion(region);
	}

	@Override
	@Transactional
	public List<Region> listRegion() {
		return regionDAO.listRegion();
	}

	@Override
	@Transactional
	public void removeRegion(Long id) {
		regionDAO.removeRegion(id);
	}

}
