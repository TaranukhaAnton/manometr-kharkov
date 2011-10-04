package ua.com.manometr.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.address.AreaDAO;
import ua.com.manometr.model.address.Area;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaDAO areaDAO;

	@Override
	@Transactional
	public void addArea(Area area) {
		areaDAO.addArea(area);
	}

	@Override
	@Transactional
	public List<Area> listArea() {
		return areaDAO.listArea();
	}

	@Override
	@Transactional
	public void removeArea(Long id) {
		areaDAO.removeArea(id);
	}

}
