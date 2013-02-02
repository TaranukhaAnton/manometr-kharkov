package ua.com.manometer.service.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.BaseDAO;
import ua.com.manometer.dao.address.CityDAO;
import ua.com.manometer.model.address.City;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityDAO cityDAO;
    
    @Autowired
	private BaseDAO baseDAO;

	@Override
	@Transactional
	public void addCity(City city) {
        baseDAO.saveOrUpdate(city);
	}

    @Override
    @Transactional
    public List<City> listCityForArea(Long areaId) {
       return cityDAO.listCityForArea(areaId);
    }

    @Override
    @Transactional
    public City getCity(Long id) {
        return (City)baseDAO.getById(id, City.class);
    }


}
