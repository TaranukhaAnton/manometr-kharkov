package ua.com.manometer.dao.address;
import ua.com.manometer.model.address.City;

import java.util.List;

public interface CityDAO {

	public void addCity(City city);

	public List<City> listCity();

	public void removeCity(Long id);

    public  List<City> listCityForArea(Long areaId);
}
