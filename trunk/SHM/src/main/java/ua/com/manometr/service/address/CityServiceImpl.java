package ua.com.manometr.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometr.dao.address.CityDAO;
import ua.com.manometr.model.address.City;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDAO cityDAO;

	@Override
	@Transactional
	public void addCity(City city) {
		cityDAO.addCity(city);
	}

	@Override
	@Transactional
	public List<City> listCity() {
		return cityDAO.listCity();
	}

	@Override
	@Transactional
	public void removeCity(Long id) {
		cityDAO.removeCity(id);
	}

}
