package ua.com.manometer.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.address.AreaDAO;
import ua.com.manometer.model.address.Area;

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
    public List<Area> listAreaForCountry(Long countryId) {
        return areaDAO.listAreaForCountry(countryId);
    }


}
